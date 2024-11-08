import React from "react";
import styled from "styled-components";
import StyledText from "./StyledText";
import { Link } from "react-router-dom";

// const StyledPlaceItemBox = styled.li`
//   position: relative;
//   /* width: calc(50% - 5.5px); */
//   width: calc(50% - 7.5px);
//   display: flex;
//   flex-direction: column;
//   cursor: pointer;
// `;

const StyledPlaceItemBox = styled(Link)`
  text-decoration: none;
  color: inherit;
  position: relative;
  width: calc(50% - 7.5px);
  display: flex;
  flex-direction: column;
  cursor: pointer;

  &:hover {
    text-decoration: none;
    color: inherit;
  }

  &:active {
    color: inherit;
    text-decoration: none;
  }

  &:focus {
    outline: none;
    color: inherit;
    text-decoration: none;
  }
`;


const StyledPlaceItemImgBox = styled.div`
  position: relative;
  padding-top: 100%;
  overflow: hidden;
`;

const StyledPlaceItemImg = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;

  border-radius: 8px;
`;

function PlaceItems({ item, to}) {
  return (
    <StyledPlaceItemBox to={to}>
      <StyledPlaceItemImgBox>
        <StyledPlaceItemImg src={item.thumbnailUrl} alt="목록 그림" />
      </StyledPlaceItemImgBox>
      <StyledText
        fontSize="1.8rem"
        fontWeight="500"
        color="#000000"
        $marginTop="10px"
      >
        {item.name}
      </StyledText>
      <StyledText
        fontSize="1.8rem"
        fontWeight="500"
        color="#000000"
        $marginTop="5px"
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
    </StyledPlaceItemBox>
  );
}

export default PlaceItems;
