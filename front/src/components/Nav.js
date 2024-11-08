import React from "react";
import styled from "styled-components";
import campIcon from "../images/camp-icon.png";
import parkIcon from "../images/park-icon.png";
import { Link, useNavigate } from "react-router-dom";

const StyledNav = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  padding: 15px 30px 0 30px;
  margin-bottom: 15px;
`;

const StyeldNavItemBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  gap: 4px;
`;

const StyledNavItemLink = styled(Link)`
  all: inherit;
  cursor: pointer;
`;

const StyeldNavItemText = styled.span`
  font-size: 1.9rem;
  font-weight: 700;
`;

const StyeldNavIconBox = styled.div`
  width: 52px;
  height: 52px;
`;
const StyeldNavIcon = styled.img`
  width: 100%;
  height: 100%;
`;

function Nav() {
  const navigate = useNavigate();

  //----------- Link로 대체할지 생각
  const handleNavClick = (category) => {
    navigate(`/places?category=${category}`);
  };

  return (
    <StyledNav>
      <StyeldNavItemBox onClick={() => handleNavClick("park")}>
        <StyeldNavIconBox>
          <StyeldNavIcon src={parkIcon} />
        </StyeldNavIconBox>
        <StyeldNavItemText>공원</StyeldNavItemText>
      </StyeldNavItemBox>
      <StyeldNavItemBox onClick={() => handleNavClick("camp")}>
        <StyeldNavIconBox>
          <StyeldNavIcon src={campIcon} />
        </StyeldNavIconBox>
        <StyeldNavItemText>캠핑</StyeldNavItemText>
      </StyeldNavItemBox>
    </StyledNav>
  );
}

export default Nav;
