import React from "react";
import campIcon from "../../assets/images/camp-icon.png";
import parkIcon from "../../assets/images/park-icon.png";
import allIcon from "../../assets/images/all-icon.png";
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
      <S.StyeldNavItemBox>
        <S.StyeldNavIconBox onClick={() => navigate("/search")}>
          <svg
            width="90%"
            height="90%"
            viewBox="0 0 24 24"
            fill="#4E5354"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M17.9375 10.9375C17.9375 14.8035 14.8035 17.9375 10.9375 17.9375C7.07151 17.9375 3.9375 14.8035 3.9375 10.9375C3.9375 7.07151 7.07151 3.9375 10.9375 3.9375C14.8035 3.9375 17.9375 7.07151 17.9375 10.9375ZM16.4088 17.4427C14.9303 18.6875 13.0215 19.4375 10.9375 19.4375C6.24308 19.4375 2.4375 15.6319 2.4375 10.9375C2.4375 6.24308 6.24308 2.4375 10.9375 2.4375C15.6319 2.4375 19.4375 6.24308 19.4375 10.9375C19.4375 13.0078 18.6973 14.9053 17.4672 16.3797L20.7225 19.6931C21.0128 19.9886 21.0086 20.4635 20.7131 20.7538C20.4176 21.044 19.9428 21.0398 19.6525 20.7444L16.4088 17.4427Z"
              fill="current"
            ></path>
          </svg>
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>검색</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
      <S.StyeldNavItemBox>
        <S.StyeldNavIconBox onClick={() => navigate("/places")}>
          {/* <S.StyledNavAllIcon>ALL</S.StyledNavAllIcon> */}
          <S.StyeldNavIcon width="80%" height="80%" src={allIcon}/>
        </S.StyeldNavIconBox>
        <S.StyeldNavIconText>전체</S.StyeldNavIconText>
      </S.StyeldNavItemBox>
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
