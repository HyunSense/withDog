import { useEffect, useState } from "react";
import { deleteSelectedBookmarks, getAllBookmarks } from "../../apis/place";
import * as S from "../../styles/BookmarkMain.Styled";

const BookmarkMain = () => {
  const [bookmarkedPlaces, setBookmarkedPlaces] = useState([]);
  const [selectedMarks, setSelectedMarks] = useState([]);

  const handleBookmarkChange = (id) => {
    console.log("id = ", id);
    // id받아온다.
    // selectedMarks 의 이전데이터에 id를 포함하고 있는지 없는지?
    // 포함하고있다면 이전데이터에서 id가 아닌것은 제거한다.(filter)
    // 포함하지 않고있다면 이전데이터에 id를 추가한다.
    setSelectedMarks((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    );
  };

  useEffect(() => {
    fetchBookmarks();
  }, []);

  const fetchBookmarks = async () => {
    try {
      const response = await getAllBookmarks();
      console.log("fetchBookmarks response = ", response);
      const apiResponse = response.data;
      setBookmarkedPlaces(apiResponse.data);
      setSelectedMarks([]);
    } catch (error) {
      console.error("fetchBookmarks error = ", error);
    }
  };

  const handleDeleteButtonClick = () => {
    if (selectedMarks.length === 0) {
      return;
    }
    console.log("selectedMarks = ", selectedMarks);
    fetchBookmarkDelete(selectedMarks);
  };

  const fetchBookmarkDelete = async (ids) => {
    try {
      console.log("ids = ", ids);
      // const data = {bookmarkPlaceIds : ids};
      await deleteSelectedBookmarks(ids);
      console.log("Success deleted bookmkars");

      fetchBookmarks();
    } catch (error) {
      console.error("fetchBookmarkDelete error = ", error);
    }
  };

  return (
    <S.StyledBookmarkMain>
      <S.StyledTitleText>
        북마크한 장소 {bookmarkedPlaces.length}
      </S.StyledTitleText>
      <S.StyledBookmarkList>
        {bookmarkedPlaces.map((place) => (
          <S.StyledBookmarkItem key={place.id}>
            <S.StyledBookmarkItemInfo>
              <S.StyledBookmarkThumbnail
                src={place.thumbnailUrl}
                alt="이미지"
              />
              <S.StyledBookmarkItemTextBox>
                <S.StyledBookmarkPlaceText>
                  {place.name}
                </S.StyledBookmarkPlaceText>
                <S.StyledBookmarkAddressText>
                  {place.address}
                </S.StyledBookmarkAddressText>
              </S.StyledBookmarkItemTextBox>
            </S.StyledBookmarkItemInfo>
            <S.StyledBookmarkCheckBox
              type="checkbox"
              id={`bookmark-${place.id}`}
              checked={selectedMarks.includes(place.id)}
              onChange={() => handleBookmarkChange(place.id)}
            />
            <S.StyledBookmarkCheckBoxLabel htmlFor={`bookmark-${place.id}`}>
              <S.StyledItemRemoveCheckBoxSvg
                width="24"
                height="24"
                viewBox="0 0 20 20"
                fill="#DDE2E3"
                xmlns="http://www.w3.org/2000/svg"
              >
                <rect
                  x="0.5"
                  y="0.5"
                  width="19"
                  height="19"
                  rx="9.5"
                  fill="current"
                ></rect>
                <path
                  fillRule="evenodd"
                  clipRule="evenodd"
                  d="M14.6549 6.27069C15.0723 6.6324 15.1174 7.26396 14.7557 7.68131L9.63127 13.5941C9.19265 14.1002 8.40738 14.1002 7.96876 13.5941L5.24433 10.4505C4.88262 10.0332 4.92773 9.40163 5.34509 9.03992C5.76244 8.67822 6.394 8.72333 6.7557 9.14068L8.80002 11.4995L13.2443 6.37145C13.606 5.9541 14.2376 5.90899 14.6549 6.27069Z"
                  fill="white"
                ></path>
              </S.StyledItemRemoveCheckBoxSvg>
              {/* <StyledBookmarkIconBox>
                <StyledBookmarkIcon
                  src={
                    selectedMarks.includes(place.id) ? bookmarkOn : bookmarkOff
                  }
                />
              </StyledBookmarkIconBox> */}
            </S.StyledBookmarkCheckBoxLabel>
          </S.StyledBookmarkItem>
        ))}
      </S.StyledBookmarkList>
      <S.StyledBookmarkRemoveButtonBox>
        <S.StyledBookmarkRemoveButton
          onClick={() => {
            handleDeleteButtonClick();
          }}
          $isActive={selectedMarks.length > 0}
          disabled={selectedMarks.length === 0}
        >
          북마크 삭제
        </S.StyledBookmarkRemoveButton>
      </S.StyledBookmarkRemoveButtonBox>
    </S.StyledBookmarkMain>
  );
};

export default BookmarkMain;
