import { Link } from "react-router-dom";
import styled from "styled-components";

export const StyledAuthHeader = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 15px;
  margin-bottom: 40px;
`;

export const StyledPrevButtonBox = styled.div`
  display: flex;
  margin-right: 32px;
`

export const StyledButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  border: 0.5px solid #dde2e3;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  background-color: #ffffff;
  width: 57px;
  height: 27px;
  font-size: 1.2rem;
  color: #000000;
`;

export const StyledAuthIcon = styled.img`
  width: 100%;
  height: 100%;
`;

export const StyledAuthLink = styled(Link)`
  /* all: inherit; */
`;

export const StyledTitleText = styled.p`
  font-size: 2.2rem;
  font-weight: 600;
  color: #000000;
`;

export const StyledText = styled.p`
  font-size: 1.2rem;
  font-weight: 500;
  color: #000000;
`;
