import React from "react";
import styled from "styled-components";
import AuthHeader from "../auth/AuthHeader";
import AuthMain from "../auth/AuthMain";
import LoginForm from "../auth/LoginForm";
import AuthMainLogo from "../auth/AuthMainLogo";

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
