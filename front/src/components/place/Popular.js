import React, { useEffect, useState } from "react";
import * as S from "../../styles/Popular.Styled";
import trendingIcon from "../../assets/images/trending-icon.png";
import PopularItems from "./PopularItems";
import { useLocation } from "react-router-dom";
import { getTop3Places } from "../../apis/place";
import { CATEGORY_MAP } from "../../constants/categoryMap";

const Popular = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const category = queryParams.get("category");

  const [popularTitle, setPopularTitle] = useState();
  const [top3Places, setTop3Places] = useState([]);

  useEffect(() => {
    const popularTitles = {
      null: "주간 인기 장소",
      camp: "주간 인기 캠핑장",
      park: "주간 인기 공원",
    };

    setPopularTitle(popularTitles[category]);

    const getTop3 = async () => {
      try {
        const categoryId = CATEGORY_MAP[category] ?? 0;
        const response = await getTop3Places({ categoryId: categoryId });

        const top3Data = response.data.data;
        console.log("top3Data = ", top3Data);
        setTop3Places([...top3Data]);
        
      } catch (error) {
        console.error("getTop3 error = ", error);
      } 
     
    };

    getTop3();
  }, [category]);

  
  return (
    <S.StyeldPopular>
      <S.StyledPopularTitleBox>
        <S.StyledPopularTitleText>{popularTitle}</S.StyledPopularTitleText>
        <S.StyledPopularTitleIconBox>
          <S.StyledPopularTitleIcon src={trendingIcon} />
        </S.StyledPopularTitleIconBox>
      </S.StyledPopularTitleBox>
      <S.StyledPopularItemList>
        {top3Places.map((place) => (
          <PopularItems
            item={place}
            key={place.id}
            to={`/places/${place.category}/${encodeURIComponent(place.name)}/${
              place.id
            }`}
          />
        ))}
      </S.StyledPopularItemList>
    </S.StyeldPopular>
  );
};

export default Popular;
