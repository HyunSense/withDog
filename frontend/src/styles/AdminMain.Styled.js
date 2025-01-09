import styled from "styled-components";

export const StyledAdminMain = styled.main`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

export const StyledNav = styled.nav`
  display: flex;
  align-items: center;
  padding: 20px 9px 0 9px;
  /* padding: 20px 16px 0 16px; */
  margin-bottom: 20px;
  gap: 0 5px;
`;

export const StyledNavButton = styled.button`
  cursor: pointer;
  background: none;
  border: none;
  font-size: 1.5rem;
  font-weight: 500;
  color: ${({$isActive}) => ($isActive ? "#ffffff" : "#000000")};
  background-color: ${({$isActive}) => ($isActive && "#022733")};
  border-radius: 8px;
  padding: 1px 7px;
  /* #022733 */ // 블루
  /* #dde2e3 */ // 회색
`;
