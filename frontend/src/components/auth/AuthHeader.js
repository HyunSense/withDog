import React from "react";
import PrevButton from "../common/PrevButton";
import * as S from "../../styles/AuthHeader.Styled";

const AuthHeader = ({ title, buttonText, link }) => {
  return (
    <S.StyledAuthHeader>
      <S.StyledPrevButtonBox>
        <PrevButton />
      </S.StyledPrevButtonBox>
      <S.StyledTitleText>{title}</S.StyledTitleText>
      <S.StyledButton>
        <S.StyledAuthLink to={link}>
          <S.StyledText>{buttonText}</S.StyledText>
        </S.StyledAuthLink>
      </S.StyledButton>
    </S.StyledAuthHeader>
  );
};

export default AuthHeader;
