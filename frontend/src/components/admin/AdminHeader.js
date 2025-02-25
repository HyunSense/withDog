import PrevButton from "../common/PrevButton";
import { useLocation } from "react-router-dom";
import * as S from "../../styles/AdminHeader.Styled";

const AdminHeader = () => {
  const location = useLocation();

  const titleText = location.pathname.includes("register")
    ? "등록"
    : "수정/삭제";

  return (
    <S.StyledAdminHeader>
      <S.StyledPrevButtonBox>
        <PrevButton />
      </S.StyledPrevButtonBox>
      <S.StyledTitleText>{titleText}</S.StyledTitleText>
      <S.StyledBalanceDummy></S.StyledBalanceDummy>
    </S.StyledAdminHeader>
  );
};

export default AdminHeader;
