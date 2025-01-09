import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as S from "../../styles/SignUpForm.Styled";
import { postSignUp } from "../../apis/auth";

// onChange의 useState 불필요 useRef 또는 초기화하여 사용할경우 e.target 사용
const SignUpForm = () => {
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
      const response = await postSignUp(signUpData);

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
    <S.StyledSignUpForm onSubmit={handleOnSubmit}>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>아이디</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="text"
          name="username"
          value={username}
          onChange={handleOnChange}
          placeholder="4~12자 영문 대소문자 숫자로만 입력"
        />
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>비밀번호</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="password"
          name="password"
          value={password}
          onChange={handleOnChange}
          placeholder="8~12자 영문 대소문자 숫자로만 입력"
        />
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>비밀번호 확인</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="password"
          name="passwordConfirm"
          value={passwordConfirm}
          onChange={handleOnChange}
          placeholder="8~12자 영문 대소문자 숫자로만 입력"
        />
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>이름</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="text"
          name="name"
          value={name}
          onChange={handleOnChange}
          placeholder="홍길동"
        />
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>이메일</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="text"
          name="email"
          value={email}
          onChange={handleOnChange}
          placeholder="withDog1234@withDog.com"
        />
      </S.StyledSignUpInputBox>
      <S.StyledSingUpButton type="submit">회원가입</S.StyledSingUpButton>
    </S.StyledSignUpForm>
  );
};

export default SignUpForm;
