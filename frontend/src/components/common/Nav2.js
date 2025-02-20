import React from "react";
import camp from "../../assets/images/camp-200x200.png";
import park from "../../assets/images/park-200x200.png";
import pension from "../../assets/images/pension-200x200.png";
import cafe from "../../assets/images/cafe-200x200.png";
import restaurant from "../../assets/images/restaurant-200x200.png";
import * as S from "../../styles/Nav.Styled";

const Nav = () => {

  return (
    <S.StyledNav>
      <S.StyeldNavItemBox to={"/search/result?types=camp"}>
        <S.StyeldNavIconBox>
          <S.StyeldNavIcon src={camp} alt="nav-icon"/>
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>캠핑</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
      <S.StyeldNavItemBox to={"/search/result?types=park"}>
        <S.StyeldNavIconBox>
          <S.StyeldNavIcon src={park} alt="nav-icon"/>
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>공원</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
      <S.StyeldNavItemBox to={"/search/result?types=pension"}>
        <S.StyeldNavIconBox>
          <S.StyeldNavIcon src={pension} alt="nav-icon"/>
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>펜션</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
      <S.StyeldNavItemBox to={"/search/result?types=cafe"}>
        <S.StyeldNavIconBox>
          <S.StyeldNavIcon src={cafe} alt="nav-icon"/>
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>카페</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
      <S.StyeldNavItemBox to={"/search/result?types=restaurant"}>
        <S.StyeldNavIconBox>
          <S.StyeldNavIcon src={restaurant} alt="nav-icon"/>
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>음식점</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
    </S.StyledNav>
  );
};

export default Nav;
