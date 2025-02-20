import { Outlet, useLocation, useNavigate } from "react-router-dom";
import * as S from "../../styles/AdminMain.Styled";

const AdminMain = () => {
  const location = useLocation();
  const navigate = useNavigate();

  //추후 <Link> 태그로 변경 필요
  return (
    <S.StyledAdminMain>
      <S.StyledNav>
        <S.StyledNavButton
          $isActive={location.pathname === "/admin/register"}
          onClick={() => navigate("register")}
        >
          등록
        </S.StyledNavButton>
        <S.StyledNavButton
          $isActive={location.pathname === "/admin/edit"}
          onClick={() => navigate("edit")}
        >
          수정/삭제
        </S.StyledNavButton>
      </S.StyledNav>
      <Outlet />
    </S.StyledAdminMain>
  );
};

export default AdminMain;
