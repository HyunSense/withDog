import styled from "styled-components";

export const StyledSignUpForm = styled.form`
  display: flex;
  flex-direction: column;
  /* width: 380px; */
  /* width: 70%; */
  width: 100%;
`;

export const StyledSignUpInputBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 25px;
`;

export const StyledSignUpInputLabel = styled.label`
  font-size: 1.5rem;
  margin-bottom: 5px;
`;

export const StyledSignUpInput = styled.input`
  display: flex;
  height: 56px;
  border: 1px solid #dde2e3;
  border-radius: 6px;
  padding: 0px 10px;
  align-items: center;
  font-size: 1.5rem;

  &:focus {
    border-color: #108fbd;
    outline: none;
  }

  &:-webkit-autofill {
    background-color: none !important;
    -webkit-box-shadow: 0 0 0px 1000px #fff inset !important;
  }

  &:-moz-autofill {
    background-color: none !important;
    box-shadow: 0 0 0px 1000px #fff inset !important;
  }
`;

export const StyledSingUpButton = styled.button`
  color: #000000;
  margin-top: 30px;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #dde2e3;
  border-radius: 6px;
  height: 45px;
  background-color: #ffffff;
  font-size: 1.8rem;
  font-weight: 700;
  cursor: pointer;
`;

export const StyledFormErrorText = styled.p`

  color: #F44336;
  padding-left: 3px;
  font-size: 1.3rem;

`;