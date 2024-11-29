import React from "react";
import campIcon from "../../assets/images/camp-icon.png";
import parkIcon from "../../assets/images/park-icon.png";
import { useNavigate } from "react-router-dom";
import * as S from "../../styles/Nav.Styled";

const Nav = () => {
  const navigate = useNavigate();

  //----------- Link로 대체할지 생각
  const handleNavClick = (category) => {
    navigate(`/places?category=${category}`);
  };

  return (
    <S.StyledNav>
      <S.StyeldNavItemBox onClick={() => handleNavClick("camp")}>
        <S.StyeldNavIconBox>
          <S.StyeldNavIcon src={campIcon} />
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>캠핑</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
      <S.StyeldNavItemBox onClick={() => handleNavClick("park")}>
        <S.StyeldNavIconBox>
          <S.StyeldNavIcon src={parkIcon} />
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>공원</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
    </S.StyledNav>
  );
};

export default Nav;
