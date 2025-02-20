import React, { useEffect, useState } from "react";
import * as S from "../../styles/Popular.Styled";
import trendingIcon from "../../assets/images/trending-icon.png";
import PopularItems from "./PopularItems";
import { getTop3Places } from "../../apis/place";

const Popular = () => {
  const [top3Places, setTop3Places] = useState([]);

  const popularTitle = "주간 인기 장소📈";

  useEffect(() => {
    getTop3();
  }, []);

  const getTop3 = async () => {
    try {
      const response = await getTop3Places();

      const top3Data = response.data.data;
      setTop3Places([...top3Data]);
    } catch (error) {
      console.error("getTop3 error = ", error);
    }
  };

  return (
    <S.StyeldPopular>
      <S.StyledPopularTitleBox>
        <S.StyledPopularTitleText>{popularTitle}</S.StyledPopularTitleText>
        {/* <S.StyledPopularTitleIconBox>
          <S.StyledPopularTitleIcon src={trendingIcon} />
        </S.StyledPopularTitleIconBox> */}
      </S.StyledPopularTitleBox>
      <S.StyledPopularItemList>
        {top3Places.map((place) => (
          <PopularItems
            item={place}
            key={place.id}
            to={`/places/${place.category}/${place.id}`}
          />
        ))}
      </S.StyledPopularItemList>
    </S.StyeldPopular>
  );
};

export default Popular;
