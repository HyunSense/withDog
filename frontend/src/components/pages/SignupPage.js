import React from "react";
import AuthHeader from "../auth/AuthHeader";
import AuthMain from "../auth/AuthMain";
import SignUpForm from "../auth/SignUpForm";

function SignupPage() {
  return (
    <div>
      <AuthHeader title="회원가입" buttonText="로그인" link="/login" />
      <AuthMain>
        <SignUpForm />
      </AuthMain>
    </div>
  );
}

export default SignupPage;
