import React, { useCallback, useEffect, useRef, useState } from "react";
import * as S from "../../styles/PlaceList.Styled";
import PlaceItems from "./PlaceItems";
import { useLocation } from "react-router-dom";
import { getAllPlaces } from "../../apis/place";
import { CATEGORY_MAP } from "../../constants/categoryMap";

const PlaceList = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const category = queryParams.get("category");
  const [places, setPlaces] = useState([]);
  const [placeListTitle, setPlaceListTitle] = useState();
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const loadMoreRef = useRef(null);

  useEffect(() => {
    setPlaces([]);
    setPage(0);
    setHasMore(true);
    const titles = {
      null: "전체 목록",
      camp: "캠핑장 목록",
      park: "공원 목록",
    };

    setPlaceListTitle(titles[category]);
  }, [category]);

  const onIntersection = useCallback(
    (entries) => {
      const firstEntity = entries[0]; // entries[0] 첫번째 entry
      // 첫번째 entry가 화면에 나타나고 && 데이터를 더많이 불러올수 있는 상태 일때
      if (firstEntity.isIntersecting && hasMore) {
        fetchMorePlaces(page, category);
      }
    },
    [hasMore, page, category]
  );

  useEffect(() => {
    // IntersectionObserver 파라미터에 onIntersection 설정
    const observer = new IntersectionObserver(onIntersection, { threshold: 1 });

    if (loadMoreRef.current) {
      // loadMoreRef가 존재하면 observer로 해당요소 관찰
      observer.observe(loadMoreRef.current);
    }

    return () => {
      // 클린업 함수 컴포넌트 언마운트시 실행
      if (loadMoreRef.current) {
        // 컴포넌트가 언마운트되거나 더 이상 관찰이 필요없을때(observer 해제할때) 반환
        observer.unobserve(loadMoreRef.current);
      }
    };
  }, [onIntersection]);

  const fetchMorePlaces = async (page, category) => {
    try {
      const categoryId = CATEGORY_MAP[category] ?? 0;
      const response = await getAllPlaces({ page: page, categoryId: categoryId });

      const apiResponse = response.data;
      const slice = apiResponse.data;
      console.log("slice = ", slice);

      setPlaces((prevPlaces) => [...prevPlaces, ...slice.content]);

      if (slice.last) {
        setHasMore(false); // 더이상 불러올 페이지가 없다면 hasMore = false
      } else {
        setPage((prevPage) => prevPage + 1);
      }
    } catch (error) {
      console.error("fetchMorePlaces Error = ", error);
    }
  };

  return (
    <S.StyledPlaceList>
      <S.StyledPlaceListTitleBox>
        <S.StyledTitleText>{placeListTitle}</S.StyledTitleText>
      </S.StyledPlaceListTitleBox>
      <S.StyledPlaceItemList>
        {places.map((place) => (
          <PlaceItems
            item={place}
            key={place.id}
            to={`/places/${place.category}/${encodeURIComponent(place.name)}/${
              place.id
            }`}
          />
        ))}
      </S.StyledPlaceItemList>
      {/* hasMore이 true일때만 loadMoreRef 랜더링 */}
      {hasMore && <div ref={loadMoreRef}></div>}
    </S.StyledPlaceList>
  );
};

export default PlaceList;
