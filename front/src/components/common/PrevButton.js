import React from "react";
import { useNavigate } from "react-router-dom";
import prevIcon from "../../assets/images/prev-icon.png";
import * as S from "../../styles/PrevButton.Styled";

const PrevButton = () => {
  const navigate = useNavigate();

  return (
    <S.StyledPrevButton onClick={() => navigate(-1)}>
      <S.StyledPrevButtonBox>
        <S.StyledPrevIcon src={prevIcon} />
      </S.StyledPrevButtonBox>
    </S.StyledPrevButton>
  );
};

export default PrevButton;
