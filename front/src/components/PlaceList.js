import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import StyledText from "./StyledText";
import PlaceItems from "./PlaceItems";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

const StyledPlaceList = styled.section`
  /* padding: 0 10px 0 30px; */
  padding: 0 30px;
  margin-bottom: 20px;
`;

const StyledPlaceListTitleBox = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 15px;
`;

const StyledPlaceItemList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  gap: 20px 15px;
  justify-content: space-between;
`;

function PlaceList() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const category = queryParams.get("category");
  const [places, setPlaces] = useState([]);
  const [placeListTitle, setPlaceListTitle] = useState();
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const loadMoreRef = useRef(null);
  // -------------------------- start

  useEffect(() => {
    setPlaces([]);
    setPage(0);
    const titles = {
      null: "전체 목록",
      camp: "캠핑장 목록",
      park: "공원 목록",
    };

    setPlaceListTitle(titles[category]);
    // if (category == null) {
    //   setPlaceListTitle("전체 목록");
    // } else if (category == "camp") {
    //   setPlaceListTitle("캠핑장 목록");
    // } else {
    //   setPlaceListTitle("공원 목록");
    // }
  }, [category]);

  useEffect(() => {
    const params = { page: page, size: 10 };
    if (category) {
      params.category = category;
    }
    const fetchPlaces = async () => {
      const response = await axios.get("http://localhost:8080/places", {
        params,
      });
      console.log("response.data.data = ", response.data);
      setPlaces((prev) => [...prev, ...response.data.data.content]);
      setHasMore(!response.data.last);
    };
    fetchPlaces();
  }, [page, category]); // 위의 useEffect에서 page변경이 있기때문에 category 의존성 필요성??

  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && hasMore) {
          setPage((prevPage) => prevPage + 1);
        }
      },
      { threshold: 1 }
    );

    if (loadMoreRef.current) observer.observe(loadMoreRef.current);
    return () => {
      if (loadMoreRef.current) observer.unobserve(loadMoreRef.current);
    };
  }, [hasMore]);
  // -------------------------- end

  return (
    <StyledPlaceList>
      <StyledPlaceListTitleBox>
        <StyledText fontSize="2.2rem" fontWeight="700">
          {placeListTitle}
        </StyledText>
      </StyledPlaceListTitleBox>
      <StyledPlaceItemList>
        {places.map((place) => (
          <PlaceItems
            item={place}
            key={place.id}
            to={`/places/${place.category}/${encodeURIComponent(place.name)}/${
              place.id
            }`}
          />
        ))}
      </StyledPlaceItemList>
      {hasMore && <div ref={loadMoreRef}></div>}
    </StyledPlaceList>
  );
}

export default PlaceList;
