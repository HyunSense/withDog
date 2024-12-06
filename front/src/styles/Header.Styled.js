import { Link } from "react-router-dom";
import styled from "styled-components";

export const StyledHeader = styled.header`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  padding: 18px 15px;
  margin-bottom: 15px;
`;

export const StyeldLogo = styled.img`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const StyledLink = styled(Link)`
  /* all: inherit; */
  width: 135px;
  height: auto;
`;

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
  /* font-size: 1.2rem; */
  /* font-weight: 500; */
  /* color: #000000; */
`;

export const StyledButtonText = styled.p`
  font-size: 1.2rem;
  font-weight: 500;
  color: #000000;
`;

export const StyledUserInfoText = styled.p`
  display: flex;
  justify-content: flex-end;
  padding-right: 1.5px;
  align-items: center;
  font-size: 1.4rem;
  font-weight: 500;
  color: #000000;
`;

export const StyledMainPageLink = styled(Link)`
  /* text-decoration: none; */
  /* outline: none; */
`;

export const StyledAuthBox = styled.div`
  display: flex;
  flex-direction: column;
  /* justify-content: flex-end; */
  /* align-items: center; */
  gap: 5px 0;
`;

export const StyledAuthButtonBox = styled.div`
  display: flex;
  align-items: center;
  gap: 0 3px;
`;
