import React, { useEffect, useState } from "react";
import styled from "styled-components";
import trendingIcon from "../images/trending-icon.png";
import PopularItems from "./PopularItems";
import campingImgTest2 from "../images/campingImgTest2.png";

const StyeldPopular = styled.section`
  padding: 0 30px;
  margin-bottom: 50px;
`;

const StyledPopularTitleBox = styled.div`
  display: flex;
  align-items: center;
  gap: 2px;
  margin-bottom: 15px;
`;

const StyledPopularTitleText = styled.span`
  font-size: 2.2rem;
  font-weight: 700;
`;

const StyledPopularTitleIconBox = styled.div`
  width: 23px;
  height: 23px;
`;

const StyledPopularTitleIcon = styled.img`
  width: 100%;
  height: 100%;
`;

const StyledPopularItemList = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 0 15px;
`;

const items = [
  {
    id: 1,
    // image: "https://example.com/image1.jpg",
    image: campingImgTest2 ,
    place: "캠핑장 1",
    price: 40000,
    address: "서울시 강남구",
  },
  {
    id: 2,
    // image: "https://example.com/image2.jpg",
    image: campingImgTest2,
    place: "캠핑장 2",
    price: 50000,
    address: "부산시 해운대구",
  },
  {
    id: 3,
    // image: "https://example.com/image3.jpg",
    image: campingImgTest2,
    place: "캠핑장 3",
    price: 60000,
    address: "충북 충주시",
  },
];

function Popular() {
  // const [items, setItems] = useState([]); // 빈배열 초기화

  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await axios.get("API_URL");
  //       setItems(response.data);
  //     } catch (error) {
  //       console.error("Error fetching data: ", error);
  //     }
  //   };

  //   fetchData();
  // }, []);

  return (
    <StyeldPopular>
      <StyledPopularTitleBox>
        <StyledPopularTitleText>주간 인기 캠핑장</StyledPopularTitleText>
        <StyledPopularTitleIconBox>
          <StyledPopularTitleIcon src={trendingIcon} />
        </StyledPopularTitleIconBox>
      </StyledPopularTitleBox>
      <StyledPopularItemList>
        {items.map((i, index) => (
          <PopularItems item={i} key={index}/>
        ))}
      </StyledPopularItemList>
    </StyeldPopular>
  );
}

export default Popular;
