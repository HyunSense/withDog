import React from "react";
import * as S from "../../styles/PlaceItem.Styled";

function PlaceItems({ item, to }) {
  return (
    <S.StyledPlaceItemBox to={to}>
      <S.StyledPlaceItemImgBox>
        <S.StyledPlaceItemImg src={item.thumbnailUrl} alt="이미지" />
      </S.StyledPlaceItemImgBox>
      <S.StyledNameText>{item.name}</S.StyledNameText>
      <S.StyledAddressText>{item.address}</S.StyledAddressText>
      <S.StyledPriceText>{item.price}원~</S.StyledPriceText>
    </S.StyledPlaceItemBox>
  );
}

export default PlaceItems;
