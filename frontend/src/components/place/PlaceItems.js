import React from "react";
import * as S from "../../styles/PlaceItem.Styled";
import { PriceDisplay } from "./PriceDisplay";

function PlaceItems({ item, to }) {
  return (
    <S.StyledPlaceItemBox to={to}>
      <S.StyledPlaceItemImgBox>
        <S.StyledPlaceItemImg src={item.thumbnailUrl} alt="이미지" />
      </S.StyledPlaceItemImgBox>
      <S.StyledNameText>{item.name}</S.StyledNameText>
      <S.StyledAddressText>{item.address}</S.StyledAddressText>
      <S.StyledPriceText>
        <PriceDisplay price={item.price} />
      </S.StyledPriceText>
    </S.StyledPlaceItemBox>
  );
}

export default PlaceItems;
