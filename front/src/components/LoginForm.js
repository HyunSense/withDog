import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { axiosInstance } from "./auth/MemberAPI";

const StyledLoginForm = styled.form`
  display: flex;
  flex-direction: column;
  /* width: 380px; */
  width: 70%;
`;

const StyledLoginInputBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 25px;
`;

const StyledLoginInputLabel = styled.label`
  font-size: 1.8rem;
  margin-bottom: 5px;
`;

const StyledLoginInput = styled.input`
  display: flex;
  height: 45px;
  border: 0.4px solid #d9d9d9;
  border-radius: 6px;
  padding: 0px 10px;
  align-items: center;
  font-size: 1.5rem;

  &:focus {
    border-color: #108fbd;
    outline: none;
  }
`;

const StyledLoginButtonBox = styled.div`
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  gap: 9px;
`;

const StyledLoginButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  border: 0.4px solid #d9d9d9;
  border-radius: 6px;
  height: 45px;
  background-color: #ffffff;
  font-size: 1.8rem;
  cursor: pointer;
`;

function LoginForm() {
  const [values, setValues] = useState({
    username: "",
    password: "",
  });

  const { username, password } = values;
  const navigate = useNavigate();

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setValues({
      ...values,
      [name]: value,
    });
  };

  const handleOnSubmit = async (e) => {
    e.preventDefault();
    localStorage.removeItem('access_token');

    try {
      // const response = await axios.post("/login", values, {
      // const response = await axiosInstance.post("/login", values, {
      //   withCredentials: true,
      // });
      const response = await axiosInstance.post("/login", values);
      console.log("response = ", response);
      const authorizationHeader = response.headers["authorization"];

      if (authorizationHeader) {
        const accessToken = authorizationHeader.replace("Bearer ", "");
        localStorage.setItem("access_token", accessToken);
      }

      if (response.status === 200) {
        navigate("/");
      }
    } catch (error) {
      console.error("로그인 오류:", error);
    }
  };

  return (
    <StyledLoginForm onSubmit={handleOnSubmit}>
      <StyledLoginInputBox>
        <StyledLoginInputLabel>아이디</StyledLoginInputLabel>
        <StyledLoginInput
          type="text"
          name="username"
          value={username}
          onChange={handleOnChange}
        />
      </StyledLoginInputBox>
      <StyledLoginInputBox>
        <StyledLoginInputLabel>비밀번호</StyledLoginInputLabel>
        <StyledLoginInput
          type="password"
          name="password"
          value={password}
          onChange={handleOnChange}
        />
      </StyledLoginInputBox>
      <StyledLoginButtonBox>
        <StyledLoginButton type="submit">로그인</StyledLoginButton>
        <StyledLoginButton>카카오 로그인</StyledLoginButton>
        <StyledLoginButton>구글 로그인</StyledLoginButton>
      </StyledLoginButtonBox>
    </StyledLoginForm>
  );
}

export default LoginForm;
