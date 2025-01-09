import { Link } from "react-router-dom";
import styled from "styled-components";

export const StyledPlaceItemBox = styled(Link)`
  text-decoration: none;
  color: inherit;
  /* position: relative; */
  width: calc(50% - 5px);
  display: flex;
  flex-direction: column;
  cursor: pointer;

`;

export const StyledNameText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  color: #000000;
  margin-top: 10px;
`;

export const StyledPriceText = styled.p`
  font-size: 1.5rem;
  font-weight: 700;
  color: #000000;
`;

export const StyledAddressText = styled.p`
  font-size: 1.3rem;
  font-weight: 400;
  color: #83898C;
  /* margin-top: 2px; */

`;

export const StyledPlaceItemImgBox = styled.div`
  position: relative;
  padding-top: 100%;
  overflow: hidden;
`;

export const StyledPlaceItemImg = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;

  border-radius: 8px;
`;