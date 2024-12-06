import React from "react";
import { useNavigate } from "react-router-dom";
import prevIcon from "../../assets/images/prev-icon.png";
import * as S from "../../styles/PrevButton.Styled";

const PrevButton = ({to}) => {
  const navigate = useNavigate();

  const handleClick = () => {
    if (to) {
      navigate(to);
    } else {
      navigate(-1);
    }
  }

  return (
    <S.StyledPrevButton onClick={() => handleClick()}>
      <S.StyledPrevButtonBox>
        <S.StyledPrevIcon src={prevIcon} />
      </S.StyledPrevButtonBox>
    </S.StyledPrevButton>
  );
};

export default PrevButton;
