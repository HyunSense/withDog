import styled from "styled-components";
import PrevButton from "../common/PrevButton";
import { useLocation } from "react-router-dom";
import { useContext } from "react";
import { AdminContext } from "./AdminContextProvider";

const StyledAdminHeader = styled.header`
  /* position: fixed; */
  position: sticky;
  top: 0;
  z-index: 1000;
  width: 100%;
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 13px 0 13px;
  margin-bottom: 20px;
  background-color: #ffffff;
`;

const StyledPrevButtonBox = styled.div`
  width: 25px;
  height: 25px;
  margin-right: 10px;
`;

const StyledEditButton = styled.button`
  cursor: pointer;
  width: 35px;
  height: auto;
  border: none;
  background: none;
  font-size: 1.7rem;
  font-weight: 500;
  color: #000000;
`;

const StyledBalanceDummy = styled.div`
  width: 25px;
  height: 25px;
  margin-left: 10px;
`;

const StyledTitleText = styled.p`
  font-size: 2rem;
  font-weight: 600;
  color: #000000;
`;

const AdminHeader = () => {
  const location = useLocation();

  const titleText = location.pathname.includes("register") ? "등록" : "수정/삭제";

  return (
    <StyledAdminHeader>
      <StyledPrevButtonBox>
        <PrevButton to="/" />
      </StyledPrevButtonBox>
      <StyledTitleText>{titleText}</StyledTitleText>
      <StyledBalanceDummy></StyledBalanceDummy>
    </StyledAdminHeader>
  );
};

export default AdminHeader;
