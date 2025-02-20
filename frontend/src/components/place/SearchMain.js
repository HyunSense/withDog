import styled from "styled-components";
import { useSearchParams } from "react-router-dom";
import { getSearch } from "../../apis/place";
import { useCallback, useEffect, useRef, useState } from "react";
import * as StyledPlaceList from "../../styles/PlaceList.Styled";
import PlaceItems from "./PlaceItems";

const StyledSearchMain = styled.main`
  /* padding: 20px 16px 0 16px; */
`;

const StyledSearch = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 15px 15px 0 15px;
  margin-bottom: 30px;
`;

const StyledSearchBox = styled.div`
  display: flex;
  align-items: center;
  border: 1px solid #dde2e3;
  border-radius: 6px;
  overflow: hidden;
  width: 100%;
  height: 40px;
`;

const StyledComboBox = styled.select`
  border: none;
  background: none;
  padding: 0 10px;
  padding-right: 24px;
  font-size: 1.4rem;
  cursor: pointer;
  height: 100%;

  appearance: none;
  -webkit-appearance: none; /* Safari 지원 */
  -moz-appearance: none; /* Firefox 지원 */
  background: url("data:image/svg+xml,%3Csvg%20width%3D%2220%22%20height%3D%2220%22%20viewBox%3D%220%200%2024%2024%22%20fill%3D%22%230E0E0E%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%3Cpath%20fill-rule%3D%22evenodd%22%20clip-rule%3D%22evenodd%22%20d%3D%22M5.1065%209.66987C5.3994%209.37697%205.87427%209.37697%206.16716%209.66987L12.0008%2015.5035L17.8344%209.66987C18.1273%209.37697%2018.6022%209.37697%2018.8951%209.66987C19.188%209.96276%2019.188%2010.4376%2018.8951%2010.7305L12.5311%2017.0945C12.2382%2017.3874%2011.7634%2017.3874%2011.4705%2017.0945L5.1065%2010.7305C4.81361%2010.4376%204.81361%209.96276%205.1065%209.66987Z%22%20fill%3D%22current%22%3E%3C%2Fpath%3E%3C%2Fsvg%3E")
    no-repeat right center;
  &:focus {
    outline: none;
  }
`;

const StyledSearchInput = styled.input`
  flex: 1;
  border: none;
  padding: 0 10px;
  font-size: 1.4rem;
  height: 100%;

  border-left: 1px solid #dde2e3;
  /* border-right: 1px solid #dde2e3; */

  &:focus {
    outline: none;
  }
`;

const StyledSearchButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  background: none;
  padding: 0 15px;
  cursor: pointer;
  font-size: 1.4rem;
  height: 100%;
`;

const StyledTitleText = styled.p`
  font-size: 1.5rem;
  padding: 0 15px;
  font-weight: 600;
  margin-bottom: 25px;
  color: #000000;
`;

const StyledPlaceListBox = styled.div`
  padding: 0 15px 30px 15px;
`;

const SearchMain = () => {
  const [places, setPlaces] = useState([]);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [totalCount, setTotalCount] = useState(null);
  const loadMoreRef = useRef(null);

  const [type, setType] = useState("name");
  const [keyword, setKeyword] = useState("");

  const [inputKeyword, setInputKeyword] = useState("");

  const [serachParams, setSearchParams] = useSearchParams();

  const handleSearch = () => {
    if (!inputKeyword.trim()) {
      alert("검색어를 입력하세요.");
      return;
    }

    
    setKeyword(inputKeyword)
    setPlaces([]);
    setPage(0);
    setHasMore(true);

    setSearchParams({ type, keyword: inputKeyword });
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSearch();
    }
  };

  //URL 변경 감지 및 초기화
  useEffect(() => {
    const currentType = serachParams.get("type") || "name";
    const currentKeyword = serachParams.get("keyword") || "";

    setType(currentType);
    setKeyword(currentKeyword);


  }, [serachParams]);

  const onIntersection = useCallback((entries) => {

      if (keyword.trim() === "") {
        return;
      }

      const firstEntity = entries[0];

      if (firstEntity.isIntersecting && hasMore) {
        fetchSearchPlaces(type, keyword, page);
      }
    },
    [hasMore, type, keyword, page]
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

  const fetchSearchPlaces = async (type, keyword, page) => {
    try {
      const response = await getSearch({
        type: type,
        keyword: keyword,
        page: page,
      });

      const apiResponse = response.data;
      const paging = apiResponse.data;
      setTotalCount(paging.totalElements);

      setPlaces((prev) => [...prev, ...paging.content]);

      if (paging.last) {
        setHasMore(false);
      } else {
        // setHasMore(true);
        setPage((prevPage) => prevPage + 1);
      }
    } catch (error) {
      console.error("fetchSearchPlaces Error = ", error);
    }
  };

  return (
    <StyledSearchMain>
      <StyledSearch>
        <StyledSearchBox>
          <StyledComboBox
            value={type}
            onChange={(e) => setType(e.target.value)}
          >
            <option value="name">이름</option>
            <option value="area">지역</option>
          </StyledComboBox>
          <StyledSearchInput
            type="text"
            placeholder="지역 또는 장소를 입력하세요."
            value={inputKeyword}
            onChange={(e) => setInputKeyword(e.target.value)}
            onKeyDown={handleKeyDown}
          />
          <StyledSearchButton onClick={handleSearch}>
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
          </StyledSearchButton>
        </StyledSearchBox>
      </StyledSearch>
      <StyledTitleText>검색 결과 {totalCount}</StyledTitleText>
      <StyledPlaceListBox>
        <StyledPlaceList.StyledPlaceItemList>
          {places.map((place) => (
            <PlaceItems
              item={place}
              key={place.id}
              to={`/places/${place.category}/${encodeURIComponent(
                place.name
              )}/${place.id}`}
            />
          ))}
        </StyledPlaceList.StyledPlaceItemList>
        {hasMore && <div ref={loadMoreRef}></div>}
      </StyledPlaceListBox>
    </StyledSearchMain>
  );
};

export default SearchMain;
