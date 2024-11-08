import React from "react";
import styled from "styled-components";
import arrowIcon from "../images/arrow-icon.png";
import homeIcon from "../images/home.png";
import bookmarkOff from "../images/bookmark-off.png";
import StyledText from "./StyledText";
import { Link } from "react-router-dom";
import BackButton from "./BackButton";

const StyledDetailHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 30px 0 30px;
  margin-bottom: 20px;
`;

const StyeldDetailIconWrapper = styled.div`
  display: flex;
  align-items: center;
  /* justify-content: center; */
  /* justify-content: flex-start; */
  justify-content: ${({ $justifyContent }) => $justifyContent};
  gap: 20px;
  flex: 1;
`;

const StyledDetailIconBox = styled.div`
  width: 25px;
  height: auto;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const StyledDetailIcon = styled.img`
  width: 100%;
  height: 100%;
`;

function DetailHeader({ name }) {
  return (
    <StyledDetailHeader>
      <StyeldDetailIconWrapper $justifyContent="flex-start">
        <BackButton>
          <StyledDetailIconBox>
            <StyledDetailIcon src={arrowIcon} />
          </StyledDetailIconBox>
        </BackButton>
        <Link to="/">
          <StyledDetailIconBox>
            <StyledDetailIcon src={homeIcon} />
          </StyledDetailIconBox>
        </Link>
      </StyeldDetailIconWrapper>
      <StyledText fontSize="2rem" fontWeight="700">
        {name}
      </StyledText>
      <StyeldDetailIconWrapper $justifyContent="flex-end">
        <StyledDetailIconBox>
          <StyledDetailIcon src={bookmarkOff} />
        </StyledDetailIconBox>
      </StyeldDetailIconWrapper>
    </StyledDetailHeader>
  );
}

export default DetailHeader;
