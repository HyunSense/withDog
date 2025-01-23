import React, { useContext } from "react";
import * as S from "../../styles/LoginForm.Styled";
import { AuthContext } from "./AuthContextProvider";
import { getSocialLogin } from "../../apis/auth";

const LoginForm = () => {
  const { login } = useContext(AuthContext);

  const handleOnSubmit = async (e) => {
    e.preventDefault();
    const form = e.target;
    const username = form.username.value;
    const password = form.password.value;
    login(username, password);
  };

  const handleSocialLogin = async (platfrom) => {

    // window.location.href = `http://localhost:8080/oauth2/authorization/${platfrom}`
    window.location.href = `https://api.withdog.store/oauth2/authorization/${platfrom}`
  }

  return (  
    <S.StyledLoginForm onSubmit={handleOnSubmit}>
      <S.StyledLoginInputBox>
        <S.StyledLoginInputLabel>아이디</S.StyledLoginInputLabel>
        <S.StyledLoginInput type="text" name="username" />
      </S.StyledLoginInputBox>
      <S.StyledLoginInputBox>
        <S.StyledLoginInputLabel>비밀번호</S.StyledLoginInputLabel>
        <S.StyledLoginInput type="password" name="password" />
      </S.StyledLoginInputBox>
      <S.StyledLoginButtonBox>
        <S.StyledLoginButton type="submit">로그인</S.StyledLoginButton>
        <S.StyeldkakaoLoginButton onClick={() => handleSocialLogin("kakao")} type="button">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="#0E0E0E">
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              fill="current"
              d="M12.0001 0.800049C5.37225 0.800049 0 4.95068 0 10.0698C0 13.2535 2.07788 16.0601 5.24205 17.7294L3.91072 22.5928C3.79309 23.0225 4.28457 23.3651 4.66197 23.116L10.4978 19.2644C10.9903 19.3119 11.4908 19.3397 12.0001 19.3397C18.6274 19.3397 24 15.1892 24 10.0698C24 4.95068 18.6274 0.800049 12.0001 0.800049Z"
            ></path>
          </svg>
          <S.StyledText $marginLeft="8px">카카오로 로그인</S.StyledText>
        </S.StyeldkakaoLoginButton>
        <S.StyledLoginButton onClick={() => handleSocialLogin("google")} type="button">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="#0E0E0E">
            <rect width="24" height="24" fill="white"></rect>
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M23.04 12.2615C23.04 11.446 22.9668 10.6619 22.8309 9.90918H12V14.3576H18.1891C17.9225 15.7951 17.1123 17.013 15.8943 17.8285V20.714H19.6109C21.7855 18.7119 23.04 15.7637 23.04 12.2615Z"
              fill="#4285F4"
            ></path>
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M11.9995 23.4998C15.1045 23.4998 17.7077 22.47 19.6104 20.7137L15.8938 17.8282C14.864 18.5182 13.5467 18.9259 11.9995 18.9259C9.00425 18.9259 6.46902 16.903 5.5647 14.1848H1.72266V17.1644C3.61493 20.9228 7.50402 23.4998 11.9995 23.4998Z"
              fill="#34A853"
            ></path>
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M5.56523 14.185C5.33523 13.495 5.20455 12.7579 5.20455 12C5.20455 11.242 5.33523 10.505 5.56523 9.81499V6.83545H1.72318C0.944318 8.38795 0.5 10.1443 0.5 12C0.5 13.8557 0.944318 15.612 1.72318 17.1645L5.56523 14.185Z"
              fill="#FBBC05"
            ></path>
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M11.9995 5.07386C13.6879 5.07386 15.2038 5.65409 16.3956 6.79364L19.694 3.49523C17.7024 1.63955 15.0992 0.5 11.9995 0.5C7.50402 0.5 3.61493 3.07705 1.72266 6.83545L5.5647 9.815C6.46902 7.09682 9.00425 5.07386 11.9995 5.07386Z"
              fill="#EA4335"
            ></path>
          </svg>
          <S.StyledText $marginLeft="8px">Google로 로그인</S.StyledText>
        </S.StyledLoginButton>
      </S.StyledLoginButtonBox>
    </S.StyledLoginForm>
  );
};

export default LoginForm;
