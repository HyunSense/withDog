import React, { useContext, useEffect, useState } from "react";
import homeIcon from "../../assets/images/home-98px.png";
import bookmarkOff from "../../assets/images/bookmark-off-98px.png";
import bookmarkOn from "../../assets/images/bookmark-on-98px.png";
import PrevButton from "../common/PrevButton";
import { AuthContext } from "../auth/AuthContextProvider";
import {
  addBookmark,
  deleteBookmark,
  getBookmarkStatus,
} from "../../apis/place";
import { Link } from "react-router-dom";

const fetchBookmarkStatus = async (placeId, setIsBookmarked) => {
  try {
    const response = await getBookmarkStatus(placeId);
    const bookmarkStatus = response.data.data.bookmarked;
    setIsBookmarked(bookmarkStatus);
  } catch (error) {
    console.log("Error fetching bookmark status = ", error);
  }
};

const updateBookmark = async (placeId, isBookmarked) => {
  try {
    if (isBookmarked) {
      await addBookmark(placeId);
    } else {
      await deleteBookmark(placeId);
    }
  } catch (error) {
    console.error("Error updating bookmark = ", error);
  }
};

const DetailHeader = ({ name, id }) => {
  const { isLogin, memberInfo } = useContext(AuthContext);
  const [isBookmarked, setIsBookmarked] = useState(false);

  useEffect(() => {
    if (isLogin) {
      fetchBookmarkStatus(id, setIsBookmarked);
    }
  }, [isLogin, id]);

  const toggleBookmark = async () => {
    if (!isLogin) {
      alert("로그인 후 이용해주세요.");
      return;
    }
    const newBookmarkStatus = !isBookmarked;
    setIsBookmarked(newBookmarkStatus);
    await updateBookmark(id, newBookmarkStatus);
  };

  return (
    <header className="flex justify-between items-center pt-5 px-3 mb-5">
      <div className="flex justify-start items-center gap-2 flex-1">
        <PrevButton />
        <Link
          to="/"
          className="flex justify-center items-center w-6 h-auto pb-0.5 cursor-pointer"
        >
          <img className="w-full h-full" src={homeIcon} alt="홈 아이콘" />
        </Link>
      </div>
      <span className="text-xl font-semibold text-neutral-800">{name}</span>
      <div className="flex justify-end items-center gap-3 flex-1">
        {memberInfo.role === "ROLE_ADMIN" && (
          <Link
            to={`/admin/edit/${id}`}
            className="text-sm font-medium text-neutral-800"
          >
            수정
          </Link>
        )}
        <div
          className="flex justify-center items-center w-6 h-auto cursor-pointer"
          onClick={toggleBookmark}
        >
          <img
            className="w-full h-full"
            src={isBookmarked ? bookmarkOn : bookmarkOff}
            alt="북마크 아이콘"
          />
        </div>
      </div>
    </header>
  );
};

export default DetailHeader;
