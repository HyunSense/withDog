import { Link } from "react-router-dom";
import styled from "styled-components";

export const StyledNav = styled.nav`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  /* justify-content: space-around; */
  align-items: center;
  /* gap: 20px; */
  gap: 12px;
  padding: 15px 15px 0 15px;
  margin-bottom: 30px;
`;

// export const StyeldNavItemBox = styled.div`
//   display: flex;
//   flex-direction: column;
//   justify-content: center;
//   align-items: center;
//   cursor: pointer;
//   gap: 4px 0;
// `;

export const StyeldNavItemBox = styled(Link)`
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
  color: #0e0e0e;
`;

export const StyeldNavIconBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 45px; */
  /* height: 45px; */
  width: 55px;
  height: 55px;
`;
export const StyeldNavIcon = styled.img`
  /* width: 100%; */
  /* height: 100%; */
  width: ${({ width }) => width || "100%"};
  height: ${({ height }) => height || "100%"};
`;

export const StyledNavAllIcon = styled.p`
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2.3rem;
  letter-spacing: -0.1rem;
  height: 100%;
  font-weight: 600;
  color: #022733;
  /* color: #108fbd; */
`;
