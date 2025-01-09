import { Link } from "react-router-dom";
import styled from "styled-components";

export const StyledDetailHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 13px 0 13px;
  margin-bottom: 20px;
`;

export const StyeldDetailIconWrapper = styled.div`
  display: flex;
  align-items: center;
  /* justify-content: center; */
  /* justify-content: flex-start; */
  justify-content: ${({ $justifyContent }) => $justifyContent};
  gap: 13px;
  flex: 1;
`;

export const StyledDetailIconBox = styled.div`
  width: 25px;
  height: auto;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

export const StyledDetailIcon = styled.img`
  width: 100%;
  height: 100%;
`;

export const StyledHomeLink = styled(Link)`
  padding-bottom: 2px;
`

export const StyledText = styled.p`

  font-size: 2rem;
  font-weight: 600;
`;