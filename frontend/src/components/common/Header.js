import React, { useContext } from "react";
import withDogLogoUpdate from "../../assets/images/withdog-logo-300x80.png";
import { AuthContext } from "../auth/AuthContextProvider";
import * as S from "../../styles/Header.Styled";
// import Loading from "./Loading";

const Header = () => {
  const { isLogin, memberInfo, logout } = useContext(AuthContext); // loading 제거


  return (
    <S.StyledHeader>
      <S.StyledLink to={"/"}>
        <S.StyeldLogo src={withDogLogoUpdate} />
      </S.StyledLink>
      {isLogin ? (
          <S.StyledAuthBox>
            <S.StyledAuthButtonBox>
              {memberInfo.role === "ROLE_ADMIN" && (
                <S.StyledButton>
                  <S.StyledLink to={"/admin"}>
                    <S.StyledButtonText>관리자</S.StyledButtonText>
                  </S.StyledLink>
                </S.StyledButton>
              )}
              <S.StyledButton>
                <S.StyledLink to={"/mypage/bookmark"}>
                  <S.StyledButtonText>북마크</S.StyledButtonText>
                </S.StyledLink>
              </S.StyledButton>
              <S.StyledButton onClick={logout}>
                <S.StyledLink to={"/"}>
                  <S.StyledButtonText>로그아웃</S.StyledButtonText>
                </S.StyledLink>
              </S.StyledButton>
            </S.StyledAuthButtonBox>
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
