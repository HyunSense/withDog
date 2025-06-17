import React, { useContext, useEffect, useState } from "react";
import homeIcon from "../../assets/images/home-98px.png";
import bookmarkOff from "../../assets/images/bookmark-off-98px.png";
import bookmarkOn from "../../assets/images/bookmark-on-98px.png";
import PrevButton from "../common/PrevButton";
import { AuthContext } from "../auth/AuthContextProvider";
import * as S from "../../styles/DetailHeader.Styled";
import {
  addBookmark,
  deleteBookmark,
  getBookmarkStatus,
} from "../../apis/place";

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
    <S.StyledDetailHeader>
      <S.StyeldDetailIconWrapper $justifyContent="flex-start">
        <PrevButton />
        <S.StyledDetailLink to="/">
          <S.StyledDetailIconBox>
            <S.StyledDetailIcon src={homeIcon} />
          </S.StyledDetailIconBox>
        </S.StyledDetailLink>
      </S.StyeldDetailIconWrapper>
      <S.StyledText fontSize="2rem" fontWeight="700">
        {name}
      </S.StyledText>
      <S.StyeldDetailIconWrapper $justifyContent="flex-end">
        {memberInfo.role === "ROLE_ADMIN" && (
          <S.StyledDetailLink to = {`/admin/edit/${id}`}>
            <S.StyledEditText>
              수정
            </S.StyledEditText>
          </S.StyledDetailLink>
        )}
        <S.StyledDetailIconBox onClick={toggleBookmark}>
          <S.StyledDetailIcon src={isBookmarked ? bookmarkOn : bookmarkOff} />
        </S.StyledDetailIconBox>
      </S.StyeldDetailIconWrapper>
    </S.StyledDetailHeader>
  );
};

export default DetailHeader;
