import styled from "styled-components";
import { Outlet, useLocation, useNavigate } from "react-router-dom";

const StyledAdminMain = styled.main`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const StyledNav = styled.nav`
  display: flex;
  align-items: center;
  padding: 20px 9px 0 9px;
  /* padding: 20px 16px 0 16px; */
  margin-bottom: 20px;
  gap: 0 5px;
`;

const StyledNavButton = styled.button`
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

const AdminMain = () => {
  const location = useLocation();
  const navigate = useNavigate();

  return (
    <StyledAdminMain>
      <StyledNav>
        <StyledNavButton $isActive={location.pathname === "/admin/register"} onClick={() => navigate("register")}>
          등록
        </StyledNavButton>
        <StyledNavButton $isActive={location.pathname === "/admin/edit"} onClick={() => navigate("edit")}>
          수정/삭제
        </StyledNavButton>
      </StyledNav>
      <Outlet />
    </StyledAdminMain>
  );
};

export default AdminMain;
