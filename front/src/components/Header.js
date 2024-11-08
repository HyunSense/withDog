import React from "react";
import styled from "styled-components";
import withDogLogo from "../images/withDog-logo.png";
import Button from "./Button";
import { Link } from "react-router-dom";

const StyledHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px 30px 0 30px;
  margin-bottom: 15px;
`;
const StyeldLogo = styled.img`
  width: 90px;
`;

const StyledLink = styled(Link)`
  all: inherit;
`;

const StyledMainPageLink = styled(Link)`
  text-decoration: none;
  outline: none;
`

function Header() {
  return (
    <StyledHeader>
      <StyledMainPageLink to={"/"}>
        <StyeldLogo src={withDogLogo} />
      </StyledMainPageLink>
      <Button $sm>
        <StyledLink to={"/login"}>로그인</StyledLink>
      </Button>
    </StyledHeader>
  );
}

export default Header;
