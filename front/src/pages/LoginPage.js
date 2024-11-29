import React from "react";
import styled from "styled-components";
import AuthHeader from "../components/auth/AuthHeader";
import AuthMain from "../components/auth/AuthMain";
import LoginForm from "../components/auth/LoginForm";
import AuthMainLogo from "../components/auth/AuthMainLogo";

const StyledLoginPageContainer = styled.div`
  /* height: 100vh; */
`

function LoginPage() {
  return (
    <StyledLoginPageContainer>
      <AuthHeader title="로그인" buttonText="회원가입" link="/sign-up"/>
      <AuthMain>
        <AuthMainLogo />
        <LoginForm />
      </AuthMain>
    </StyledLoginPageContainer>
  );
}

export default LoginPage;
