import React from "react";
import AuthHeader from "../components/auth/AuthHeader";
import AuthMain from "../components/auth/AuthMain";
import SignUpForm from "../components/auth/SignUpForm";
import styled from "styled-components";
import AuthMainLogo from "../components/auth/AuthMainLogo";

const StyledLoginPageContainer = styled.div`
  /* height: 100vh; */
  /* background-color: #ffffff; */
`;

function SignupPage() {
  return (
    <StyledLoginPageContainer>
      <AuthHeader title="회원가입" buttonText="로그인" link="/login" />
      <AuthMain>
        <SignUpForm />
      </AuthMain>
    </StyledLoginPageContainer>
  );
}

export default SignupPage;
