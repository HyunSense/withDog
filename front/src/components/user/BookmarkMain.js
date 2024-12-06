import styled from "styled-components";
import withDogLogo from "../../assets/images/withDog-logo-192x192.png";
import bookmarkOff from "../../assets/images/bookmark-off-98px.png";
import bookmarkOn from "../../assets/images/bookmark-on-98px.png";
import { useEffect, useState } from "react";
import { getAllBookmarks } from "../apis/place";

const StyledBookmarkMain = styled.main`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  padding: 20px 16px 0 16px;
  flex: 1 0 0px;
  overflow-y: auto;
  ::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none;
`;

const StyledTitleText = styled.p`
  font-size: 1.5rem;
  font-weight: 600;
  /* margin-bottom: 20px; */
  color: #000000;
`;

const StyledBookmarkList = styled.div`
  width: 100%;
  height: 100%;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 25px 0;
  background-color: #ffffff;
`;

const StyledBookmarkItem = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const StyledBookmarkItemInfo = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  gap: 0 10px;
`;

const StyledBookmarkItemTextBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px 0;
`;

const StyledBookmarkPlaceText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  color: #000000;
`;

const StyledBookmarkAddressText = styled.p`
  font-size: 1.3rem;
  font-weight: 500;
  color: #83898c;
`;

const StyledBookmarkThumbnail = styled.img`
  width: 48px;
  height: 48px;
  border: 0.5px solid #dde2e3;
  border-radius: 50%;

  object-fit: cover;
`;

const StyledBookmarkCheckBox = styled.input`
  display: none;

  &:checked + label svg {
    fill: #61d377;
  }
`;

const StyledBookmarkCheckBoxLabel = styled.label`
  pointer-events: auto;
  cursor: pointer;
`;

const StyledItemRemoveCheckBoxSvg = styled.svg`
  display: flex;
  cursor: pointer;
  align-items: center;
`;

const StyledBookmarkIconBox = styled.div`
  width: 25px;
  height: 25px;
  border: none;
  background: none;
`;

const StyledBookmarkIcon = styled.img`
  width: 100%;
  height: 100%;
`;

const StyledBookmarkRemoveButtonBox = styled.div`
  position: sticky;
  bottom: 0px;
  width: 100%;
  padding: 12px 16px 35px 16px;
  border-top: 1px solid #eff2f2;
  background-color: #ffffff;
`;

const StyledBookmarkRemoveButton = styled.button`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  border: none;
  border-radius: 8px;
  font-size: 1.5rem;
  font-weight: 600;
  height: 48px;
  color: #ffffff;
  background-color: ${({ $isActive }) => ($isActive ? "#022733" : "#dde2e3")};
  cursor: ${({ $isActive }) => $isActive && "pointer"};
`;

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
      // setSelectedMarks((prev) => [...prev, ...apiResponse.data.map((p) => p.id)]);
      // setSelectedMarks(apiResponse.data.map((p) => p.id));
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
    } catch (error) {
      console.error("fetchBookmarkDelete error = ", error);
    }
  };

  return (
    <StyledBookmarkMain>
      <StyledTitleText>북마크한 장소 {bookmarkedPlaces.length}</StyledTitleText>
      <StyledBookmarkList>
        {bookmarkedPlaces.map((place) => (
          <StyledBookmarkItem key={place.id}>
            <StyledBookmarkItemInfo>
              <StyledBookmarkThumbnail src={place.thumbnailUrl} alt="이미지"/>
              <StyledBookmarkItemTextBox>
                <StyledBookmarkPlaceText>{place.name}</StyledBookmarkPlaceText>
                <StyledBookmarkAddressText>
                  {place.address}
                </StyledBookmarkAddressText>
              </StyledBookmarkItemTextBox>
            </StyledBookmarkItemInfo>
            <StyledBookmarkCheckBox
              type="checkbox"
              id={`bookmark-${place.id}`}
              checked={selectedMarks.includes(place.id)}
              onChange={() => handleBookmarkChange(place.id)}
            />
            <StyledBookmarkCheckBoxLabel htmlFor={`bookmark-${place.id}`}>
            <StyledItemRemoveCheckBoxSvg
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
          </StyledItemRemoveCheckBoxSvg>
              {/* <StyledBookmarkIconBox>
                <StyledBookmarkIcon
                  src={
                    selectedMarks.includes(place.id) ? bookmarkOn : bookmarkOff
                  }
                />
              </StyledBookmarkIconBox> */}
            </StyledBookmarkCheckBoxLabel>
          </StyledBookmarkItem>
        ))}
      </StyledBookmarkList>
      <StyledBookmarkRemoveButtonBox>
        <StyledBookmarkRemoveButton onClick={() => {handleDeleteButtonClick()}}
          $isActive={selectedMarks.length > 0}
          disabled={selectedMarks.length === 0}
          >북마크 삭제</StyledBookmarkRemoveButton>
      </StyledBookmarkRemoveButtonBox>
    </StyledBookmarkMain>
  );
};

export default BookmarkMain;
