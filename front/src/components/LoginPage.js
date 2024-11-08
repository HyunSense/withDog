import React from "react";
import styled from "styled-components";
import AuthHeader from "./AuthHeader";
import AuthMain from "./AuthMain";
import LoginForm from "./LoginForm";

const StyledLoginPageContainer = styled.div`
  height: 100vh;
`

function LoginPage() {
  return (
    <StyledLoginPageContainer>
      <AuthHeader title="로그인" buttonText="회원가입" link="/sign-up"/>
      <AuthMain>
        <LoginForm />
      </AuthMain>
    </StyledLoginPageContainer>
  );
}

export default LoginPage;
