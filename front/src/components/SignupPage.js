import React from "react";
import AuthHeader from "./AuthHeader";
import AuthMain from "./AuthMain";
import SignUpForm from "./SignUpForm";
import styled from "styled-components";

const StyledLoginPageContainer = styled.div`
  height: 100vh;
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
