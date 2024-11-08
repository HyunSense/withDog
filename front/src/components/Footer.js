import React from "react";
import styled from "styled-components";
import withDogLogo from "../images/withDog-logo.png";
import StyledText from "./StyledText";

const StyledFooter = styled.footer`
  padding: 10px 30px;
  background-color: #f8fafb;
`;

const StyeldFooterLogo = styled.img`
  width: 60px;
`;

const StyeldFooterInfo = styled.div`
  display: flex;
  flex-direction: column;
`;

const StyledFooterTextWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 5px 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eaeeec;
`;

function Footer() {
  return (
    <StyledFooter>
      <StyledFooterTextWrapper>
        <StyledText color="#2e3132">서비스 이용 약관</StyledText>
        <StyledText color="#2e3132">개인정보 처리 방침</StyledText>
        <StyledText color="#2e3132">캠핑장 제휴 신청</StyledText>
      </StyledFooterTextWrapper>
      {/* <StyledFooterHr /> */}
        <StyledText color="#2e3132" $padding="10px 0 5px 0">(주)WithDog</StyledText>
        <StyledText color="#2e3132">본 사이트는 개인 포트폴리오 웹페이지 입니다.</StyledText>
    </StyledFooter>
  );
}

export default Footer;
