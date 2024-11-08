import React from "react";
import styled from "styled-components";
import StyledText from "./StyledText";
import Button from "./Button";
import arrowIcon from "../images/arrow-icon.png";
import { Link } from "react-router-dom";

const StyledAuthHeader = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 60px;
  margin-bottom: 40px;
`;

const StyledAuthIconBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 25px;
  height: auto;
  margin-right: 40px;
`;

const StyledAuthIcon = styled.img`
  width: 100%;
  height: 100%;
`;

const StyledAuthLink = styled(Link)`
  all: inherit;
`;

function AuthHeader({ title, buttonText, link }) {
  return (
    <StyledAuthHeader>
      <StyledAuthIconBox>
        <StyledAuthIcon src={arrowIcon} />
      </StyledAuthIconBox>
      <StyledText fontSize="2.2rem" fontWeight="700">
        {title}
      </StyledText>
      {/* <Button sm>{buttonText} */}
      <Button $sm>
        <StyledAuthLink to={link}>{buttonText}</StyledAuthLink>
      </Button>
    </StyledAuthHeader>
  );
}

export default AuthHeader;
