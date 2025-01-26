import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as S from "../../styles/SignUpForm.Styled";
import { postSignUp } from "../../apis/auth";
import { useForm } from "react-hook-form";

// onChange의 useState 불필요 useRef 또는 초기화하여 사용할경우 e.target 사용
const SignUpForm = () => {

  const navigate = useNavigate();

  const {
    register, // 입력 필드를 react-hook-form에 등록
    handleSubmit, // 폼 제출 핸들러 (onSubmit 함수와 연결)
    watch, // 입력값을 실시간으로 관찰
    formState: { errors }, // 에러 상태 및 기타 폼 상태 정보
  } = useForm({ mode: "onChange" }); // 유효성 검사 실행 시점 설정

  const onSubmit = async (data) => {

    const {passwordConfirm, ...formData} = data;
    console.log("formData = ", formData);
    try {
      await postSignUp(formData);

      console.log("회원가입 완료");

      alert("회원가입이 완료되었습니다.");
      navigate("/login");
      
    } catch (error) {
      if (error.response.status === 400 && error.response.data.code === "DU") {
        alert("중복된 아이디 입니다.");
      }
      console.error("회원가입 오류:", error);
    }
  };

  return (
    <S.StyledSignUpForm onSubmit={handleSubmit(onSubmit)}>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>아이디*</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="text"
          name="username"
          placeholder="4~12자 영문 대소문자와 숫자로만 입력"
          {...register("username", {
            required: "아이디를 입력해주세요.",
            pattern: {
              value: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{4,12}$/,
              message: "아이디는 4~12자 영문 대소문자 + 숫자로만 입력해주세요.",
            },
          })}
        />
        {errors?.username && <S.StyledFormErrorText>{errors.username.message}</S.StyledFormErrorText>}
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>비밀번호*</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="password"
          name="password"
          placeholder= "8~12자 영문 대소문자와 숫자, 특수문자는 선택 입력"
          {...register("password", {
            required: "비밀번호를 입력해주세요.",
            pattern: {
              value: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d!@#$%^&*()_+={}:;"'<>,.?/-]{8,12}$/,
              message: "비밀번호는 8~12자 영문 대소문자와 숫자, 특수문자는 선택 입력해주세요.",
            },
          })}
        />
        {errors?.password && <S.StyledFormErrorText>{errors.password.message}</S.StyledFormErrorText>}
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>비밀번호 확인*</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="password"
          name="passwordConfirm"
          // placeholder= "(필수) 8~12자 영문 대소문자와 숫자, 특수문자는 선택 입력"
          {...register("passwordConfirm", {
            required: "비밀번호 확인을 입력해주세요.",
            validate: (value) =>
              value === watch("password") || "비밀번호가 일치하지 않습니다.",
          })}
        />
        {errors?.passwordConfirm && <S.StyledFormErrorText>{errors.passwordConfirm.message}</S.StyledFormErrorText>}
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>이름*</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="text"
          name="name"
          placeholder="홍길동"
          {...register("name", {
            required: "이름을 입력해주세요.",
          })}
        />
        {errors?.name && <S.StyledFormErrorText>{errors.name.message}</S.StyledFormErrorText>}
      </S.StyledSignUpInputBox>
      <S.StyledSignUpInputBox>
        <S.StyledSignUpInputLabel>이메일</S.StyledSignUpInputLabel>
        <S.StyledSignUpInput
          type="text"
          name="email"
          placeholder="(선택) withDog1234@withDog.com"
          {...register("email", {
            validate: {
              // notEmpty: (value) => value.trim() !== "",
              pattern: (value) => value.trim() === "" || /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value) || "올바른 이메일 주소를 입력해주세요.",
            }
          })}
        />
        {errors?.email && <S.StyledFormErrorText>{errors.email.message}</S.StyledFormErrorText>}
      </S.StyledSignUpInputBox>
      <S.StyledSingUpButton type="submit">회원가입</S.StyledSingUpButton>
    </S.StyledSignUpForm>
  );
};

export default SignUpForm;
