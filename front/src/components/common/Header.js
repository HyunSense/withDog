import React, { useContext } from "react";
import withDogLogoUpdate from "../../assets/images/withdog-logo-update.png";
import { AuthContext } from "../auth/AuthContextProvider";
import * as S from "../../styles/Header.Styled";

const Header = () => {
  const { isLogin, memberInfo, logout } = useContext(AuthContext); // loading 제거
  // const { loading } = useLoading();
  console.log("isLogin = ", isLogin);
  console.log("memberInfo = ", memberInfo);

  // if (loading) {
  //   return <Loading />;
  // }

  return (
    <S.StyledHeader>
      <S.StyledLink to={"/"}>
        <S.StyeldLogo src={withDogLogoUpdate} />
      </S.StyledLink>
      {isLogin ? (
        <S.StyledAuthBox>
          <S.StyledUserInfoText>{memberInfo.username}님</S.StyledUserInfoText>
          {memberInfo.role === "ROLE_ADMIN" && (
            <S.StyledButton>
              <S.StyledLink to={"/register"}>
                <S.StyledButtonText>장소등록</S.StyledButtonText>
              </S.StyledLink>
            </S.StyledButton>
          )}
          <S.StyledButton onClick={logout}>
            <S.StyledLink to={"/"}>
              <S.StyledButtonText>로그아웃</S.StyledButtonText>
            </S.StyledLink>
          </S.StyledButton>
        </S.StyledAuthBox>
      ) : (
        <S.StyledButton>
          <S.StyledLink to={"/login"}>
            <S.StyledButtonText>로그인</S.StyledButtonText>
          </S.StyledLink>
        </S.StyledButton>
      )}
    </S.StyledHeader>
  );
};

export default Header;
