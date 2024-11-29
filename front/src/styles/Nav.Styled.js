import styled from "styled-components";

export const StyledNav = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  padding: 15px 15px 0 15px;
  margin-bottom: 30px;
`;

export const StyeldNavItemBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  gap: 4px 0;
`;

export const StyeldNavIconText = styled.span`
  font-size: 1.3rem;
  font-weight: 500;
  line-height: 1;
`;

export const StyeldNavIconBox = styled.div`
  width: 45px;
  height: 45px;
  /* width: 80%; */
`;
export const StyeldNavIcon = styled.img`
  width: 100%;
  height: 100%;
`;
