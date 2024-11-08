import React from "react";
import styled from "styled-components";
import StyledText from "./StyledText";

const StyledPopularItemBox = styled.div`
  display: flex;
  flex-direction: column;
`;

const StyledPopularItemImgBox = styled.div`
  /* width: 208px; */
  /* height: 208px; */
`;

const StyledPopularItemImg = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 8px;
`;

function PopularItems({ item }) {
  return (
    <StyledPopularItemBox>
      <StyledPopularItemImgBox>
        <StyledPopularItemImg src={item.image} />
      </StyledPopularItemImgBox>
      <StyledText
        fontSize="1.8rem"
        fontWeight="500"
        color="#000000"
        $marginTop="10px"
      >
        {item.place}
      </StyledText>
      <StyledText fontSize="1.7rem" fontWeight="700" color="#000000">
        {item.price}
        <span>원~</span>
      </StyledText>
      <StyledText
        fontSize="1.5rem"
        fontWeight="300"
        color="#83898C"
        $marginTop="2px"
      >
        {item.address}
      </StyledText>
    </StyledPopularItemBox>
  );
}

export default PopularItems;
