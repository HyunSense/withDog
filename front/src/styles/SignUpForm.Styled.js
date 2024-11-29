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