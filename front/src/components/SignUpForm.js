import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const StyledSignUpForm = styled.form`
  display: flex;
  flex-direction: column;
  /* width: 380px; */
  width: 70%;
`;

const StyledSignUpInputBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 25px;
`;

const StyledSignUpInputLabel = styled.label`
  font-size: 1.8rem;
  margin-bottom: 5px;
`;

const StyledSignUpInput = styled.input`
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

const StyledSingUpButton = styled.button`
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 0.4px solid #d9d9d9;
  border-radius: 6px;
  height: 45px;
  background-color: #ffffff;
  font-size: 1.8rem;
  font-weight: 700;
  cursor: pointer;
`;

function SignUpForm() {
  const [values, setValues] = useState({
    username: "",
    password: "",
    passwordConfirm: "",
    name: "",
    email: "",
  });

  const { username, password, passwordConfirm, name, email } = values;
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
    console.log(values);

    if (password !== passwordConfirm) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    const signUpData = {
      username,
      password,
      name,
      email,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/sign-up",
        signUpData
      );

      console.log("response=", response);

      if (response.status === 200) {
        console.log("회원가입 완료");
        setValues({
          username: "",
          password: "",
          passwordConfirm: "",
          name: "",
          email: "",
        });

        navigate("/login");
      } else {
        console.log("server error");
      }
    } catch (error) {
      console.error("회원가입 오류:", error);
    }
  };

  return (
    <StyledSignUpForm onSubmit={handleOnSubmit}>
      <StyledSignUpInputBox>
        <StyledSignUpInputLabel>아이디</StyledSignUpInputLabel>
        <StyledSignUpInput
          type="text"
          name="username"
          value={username}
          onChange={handleOnChange}
          placeholder="4~12자 영문 대소문자 숫자로만 입력"
        />
      </StyledSignUpInputBox>
      <StyledSignUpInputBox>
        <StyledSignUpInputLabel>비밀번호</StyledSignUpInputLabel>
        <StyledSignUpInput
          type="password"
          name="password"
          value={password}
          onChange={handleOnChange}
          placeholder="8~12자 영문 대소문자 숫자로만 입력"
        />
      </StyledSignUpInputBox>
      <StyledSignUpInputBox>
        <StyledSignUpInputLabel>비밀번호 확인</StyledSignUpInputLabel>
        <StyledSignUpInput
          type="password"
          name="passwordConfirm"
          value={passwordConfirm}
          onChange={handleOnChange}
          placeholder="8~12자 영문 대소문자 숫자로만 입력"
        />
      </StyledSignUpInputBox>
      <StyledSignUpInputBox>
        <StyledSignUpInputLabel>이름</StyledSignUpInputLabel>
        <StyledSignUpInput
          type="text"
          name="name"
          value={name}
          onChange={handleOnChange}
          placeholder="홍길동"
        />
      </StyledSignUpInputBox>
      <StyledSignUpInputBox>
        <StyledSignUpInputLabel>이메일</StyledSignUpInputLabel>
        <StyledSignUpInput
          type="text"
          name="email"
          value={email}
          onChange={handleOnChange}
          placeholder="withDog1234@withDog.com"
        />
      </StyledSignUpInputBox>
      <StyledSingUpButton type="submit">회원가입</StyledSingUpButton>
    </StyledSignUpForm>
  );
}

export default SignUpForm;
