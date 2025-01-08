import React from "react";
import * as S from "../../styles/PopularItems.Styled";
import { PriceDisplay } from "./PriceDisplay";

const PopularItems = ({ item, to }) => {
  return (
    <S.StyledPopularItemBox to={to}>
      <S.StyledPopularItemImgBox>
        <S.StyledPopularItemImg src={item.thumbnailUrl} alt="인기 장소" />
      </S.StyledPopularItemImgBox>
      <S.StyledNameText>{item.name}</S.StyledNameText>
      <S.StyledPriceText>
        <PriceDisplay price={item.price} />
      </S.StyledPriceText>
      <S.StyledAddressText>{item.address}</S.StyledAddressText>
    </S.StyledPopularItemBox>
  );
};

export default PopularItems;
