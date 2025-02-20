import { useCallback, useEffect, useRef, useState } from "react";
import { getSearchPlaces } from "../../apis/place";
import PlaceItems from "./PlaceItems";
import * as StyledPlaceList from "../../styles/PlaceList.Styled";
import styled from "styled-components";
import PrevButton from "../common/PrevButton";
import { useSearchParams } from "react-router-dom";
import SearchModal from "../pages/SearchModal";

const StyledSearchHeader = styled.header`
  position: sticky;
  top: 0;
  z-index: 1000;
  width: 100%;
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 16px 20px 16px;
  background-color: #ffffff;
`;

const StyledPrevButtonBox = styled.div`
  width: 25px;
  height: 25px;
  margin-right: 10px;
`;

const StyledDummyBox = styled.div`
  width: 25px;
  height: 25px;
  margin-left: 10px;
`;

const StyledTitleText = styled.p`
  font-size: 2rem;
  font-weight: 600;
  color: #000000;
`;

const StyledSearchResultInputBox = styled.div`
  position: sticky;
  top: 52px;
  z-index: 999;
  /* margin: 10px 0 0 0; */
  padding: 0 16px 20px 16px;
  background-color: #ffffff;
`;

const StyledSearchResultInput = styled.div`
  display: flex;
  align-items: center;
  gap: 0 10px;
  width: 100%;
  height: 55px;
  border: 1px solid #dde2e3;
  border-radius: 25px;
  padding: 0 20px;
  cursor: pointer;
  background-color: #f8fafb;
`;

// StyledSearchIconBox 통합필요?
const StyledSearchResultIconBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
const StyledSearchResultInputTextBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 3px 0;
`;

const StyledSearchResultInputText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  color: #4e5354;
`;

const StyledSearchResultInputInfo = styled.p`
  font-size: 1.2rem;
  font-weight: 500;
  color: #4e5354;
`;

const StyledSearchResultText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  padding: 0 16px;
  /* margin-top: 15px; */
`;

const StyledPlaceListBox = styled.div`
  margin-top: 20px;
  padding: 0 16px 25px 16px;
`;

const SearchResult = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [places, setPlaces] = useState([]);
  const [page, setPage] = useState(0); // page는 0부터 시작
  const [hasMore, setHasMore] = useState(true);
  const loadMoreRef = useRef(null);
  const [searchParams] = useSearchParams();
  const searchParamsRef = useRef(searchParams);

  useEffect(() => {
    setPlaces([]);
    setHasMore(true);
    setPage(0);
  }, [searchParams]);

  // useRef를 통해 searchParams 최신값 유지
  useEffect(() => {
    searchParamsRef.current = searchParams;
  }, [searchParams]);

  const fetchSearchResult = useCallback(async (pageNum) => {
    try {
      const params = {
        ...Object.fromEntries(searchParamsRef.current.entries()),
        page: pageNum,
        size: 10,
      };
      const response = await getSearchPlaces(params);
      const { content, sliceInfo } = response.data.data;
      setPlaces((prev) => [...prev, ...content]);
      if (sliceInfo.last) {
        setHasMore(false);
      } else {
        setPage((prev) => prev + 1);
      }
    } catch (error) {
      console.log("검색 결과 오류 = ", error);
    }
  }, []);

  const onIntersection = useCallback(
    (entries) => {
      const firstEntity = entries[0];
      if (firstEntity.isIntersecting && hasMore) {
        fetchSearchResult(page);
      }
    },
    [hasMore, page, fetchSearchResult]
  );

  useEffect(() => {
    const observer = new IntersectionObserver(onIntersection, { threshold: 1 });
    const currentRef = loadMoreRef.current;

    if (currentRef) {
      observer.observe(currentRef);
    }

    return () => {
      if (currentRef) {
        observer.unobserve(currentRef);
      }
    };
  }, [onIntersection]);

  return (
    <>
      <StyledSearchHeader>
        <StyledPrevButtonBox>
          <PrevButton to="/" />
        </StyledPrevButtonBox>
        <StyledTitleText>검색</StyledTitleText>
        <StyledDummyBox></StyledDummyBox>
      </StyledSearchHeader>
      <StyledSearchResultInputBox>
        <StyledSearchResultInput onClick={() => setIsModalOpen(true)}>
          <StyledSearchResultIconBox>
            <svg
              width="22"
              height="22"
              viewBox="0 0 24 24"
              fill="#4E5354"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fillRule="evenodd"
                clipRule="evenodd"
                d="M17.9375 10.9375C17.9375 14.8035 14.8035 17.9375 10.9375 17.9375C7.07151 17.9375 3.9375 14.8035 3.9375 10.9375C3.9375 7.07151 7.07151 3.9375 10.9375 3.9375C14.8035 3.9375 17.9375 7.07151 17.9375 10.9375ZM16.4088 17.4427C14.9303 18.6875 13.0215 19.4375 10.9375 19.4375C6.24308 19.4375 2.4375 15.6319 2.4375 10.9375C2.4375 6.24308 6.24308 2.4375 10.9375 2.4375C15.6319 2.4375 19.4375 6.24308 19.4375 10.9375C19.4375 13.0078 18.6973 14.9053 17.4672 16.3797L20.7225 19.6931C21.0128 19.9886 21.0086 20.4635 20.7131 20.7538C20.4176 21.044 19.9428 21.0398 19.6525 20.7444L16.4088 17.4427Z"
                fill="current"
              ></path>
            </svg>
          </StyledSearchResultIconBox>
          <StyledSearchResultInputTextBox>
            <StyledSearchResultInputText>
              {searchParams.get("keyword") || "어디로 떠날까요?"}
            </StyledSearchResultInputText>
            {/* <StyledSearchResultInputInfo> */}
            {/* 검색조건 캠핑,펜션 외 2 없으면 필터 */}
            {/* </StyledSearchResultInputInfo> */}
          </StyledSearchResultInputTextBox>
        </StyledSearchResultInput>
      </StyledSearchResultInputBox>
      <StyledSearchResultText>검색결과 0건</StyledSearchResultText>
      <StyledPlaceListBox>
        <StyledPlaceList.StyledPlaceItemList>
          {places.map((place) => (
            <PlaceItems
              item={place}
              key={place.id}
              to={`/places/${place.category}/${place.id}`}
            />
          ))}
        </StyledPlaceList.StyledPlaceItemList>
        {hasMore && <div ref={loadMoreRef}></div>}
      </StyledPlaceListBox>
      {isModalOpen && <SearchModal onClose={() => setIsModalOpen(false)} />}
    </>
  );
};

export default SearchResult;
