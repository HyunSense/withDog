import React from "react";
import withDogLogoFooter from "../../assets/images/withDog-logo-black.png";
import githubIcon from "../../assets/images/github-icon-80px-black.png";
import notionIcon from "../../assets/images/notion-icon-80px-black.png";
import googleIcon from "../../assets/images/google-icon-80px-black.png";
import * as S from "../../styles/Footer.Styled";

const Footer = () => {
  return (
    <S.StyledFooter>
      <S.StyledIconBox>
        <S.StyledLogoBox>
          <S.StyledLogo src={withDogLogoFooter} />
        </S.StyledLogoBox>
        <S.StyledSnsBox>
          <a
            href="https://github.com/HyunSense"
            target="_blank"
            rel="noreferrer"
          >
            <S.StyledSnsLogoBox>
              <S.StyledSnsLogo src={githubIcon} />
            </S.StyledSnsLogoBox>
          </a>
          <a
            href="https://hyunsense.notion.site/Study-cd2cc1f341b44e94886429fc39fd5b4e?pvs=4"
            target="_blank"
            rel="noreferrer"
          >
            <S.StyledSnsLogoBox>
              <S.StyledSnsLogo src={notionIcon} />
            </S.StyledSnsLogoBox>
          </a>
          <a
            href="https://drive.google.com/file/d/1D3DteCCg0XqvqKuORpXA-HdEy5dDjEBL/view?usp=sharing"
            target="_blank"
            rel="noreferrer"
          >
            <S.StyledSnsLogoBox>
              <S.StyledSnsLogo src={googleIcon} />
            </S.StyledSnsLogoBox>
          </a>
        </S.StyledSnsBox>
      </S.StyledIconBox>
      <S.StyledTextBox>
        <S.StyledText $fontWeight="600">WithDog Project</S.StyledText>
        <S.StyledText>대표 : 현재훈</S.StyledText>
        <S.StyledText>주소 : 인천광역시 연수구 랜드마크로 20</S.StyledText>
        <S.StyledText>이메일 : hyunsense1022@gmail.com</S.StyledText>
        <S.StyledText>전화번호 : 010-4394-0571</S.StyledText>
        <S.StyledText $fontWeight="600">
          본 사이트는 개인 포트폴리오 웹 페이지 입니다.
        </S.StyledText>
      </S.StyledTextBox>
    </S.StyledFooter>
  );
};

export default Footer;
