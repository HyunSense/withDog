import React from "react";
import AuthHeader from "../auth/AuthHeader";
import AuthMain from "../auth/AuthMain";
import LoginForm from "../auth/LoginForm";
import AuthMainLogo from "../auth/AuthMainLogo";

function LoginPage() {
  return (
    <div>
      <AuthHeader title="로그인" buttonText="회원가입" link="/sign-up" />
      <AuthMain>
        <AuthMainLogo />
        <LoginForm />
      </AuthMain>
    </div>
  );
}

export default LoginPage;
