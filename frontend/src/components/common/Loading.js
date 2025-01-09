import React from "react";
import { MoonLoader } from "react-spinners";
import * as S from "../../styles/Loading.Styled";

const Loading = () => {
  return (
    <S.StyledLoadingContainer>
      <MoonLoader color="#1491BE" speedMultiplier="0.8" />
      <S.StyledText>잠시만 기다려주세요.</S.StyledText>
    </S.StyledLoadingContainer>
  );
};

export default Loading;
