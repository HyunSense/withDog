import { Link } from "react-router-dom";
import styled from "styled-components";

export const StyledPopularItemBox = styled(Link)`
  text-decoration: none;
  color: inherit;
  cursor: pointer;
  width: calc(33% - 1px);
  display: flex;
  flex-direction: column;
`;

export const StyledPopularItemImgBox = styled.div`
  position: relative;
  padding-top: 100%;
  overflow: hidden;
`;

export const StyledPopularItemImg = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
`;

export const StyledNameText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  color: #000000;
  margin-top: 10px;
`;

export const StyledPriceText = styled.p`
  font-size: 1.5rem;
  font-weight: 600;
  color: #000000;
`;

export const StyledAddressText = styled.p`
  font-size: 1.3rem;
  font-weight: 400;
  color: #83898C;
  /* margin-top: 2px; */

`;