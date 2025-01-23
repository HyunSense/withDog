import styled from "styled-components";

export const StyledLoginForm = styled.form`
  display: flex;
  flex-direction: column;
  /* width: 380px; */
  /* width: 70%; */
  width: 100%;
`;

export const StyledLoginInputBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 25px;
`;

export const StyledLoginInputLabel = styled.label`
  font-size: 1.5rem;
  margin-bottom: 5px;
`;

export const StyledLoginInput = styled.input`
  display: flex;
  height: 56px;
  border: 1px solid #dde2e3;
  border-radius: 6px;
  padding: 0px 10px;
  align-items: center;
  font-size: 1.5rem;
  appearance: none;

  &:focus {
    border-color: #108fbd;
    outline: none;
  }
`;

export const StyledLoginButtonBox = styled.div`
  margin-top: 30px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  gap: 9px;
`;

export const StyledLoginButton = styled.button`
  /* #dde2e3 */
  color: #0e0e0e;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #dde2e3;
  border-radius: 6px;
  height: 45px;
  background-color: #ffffff;
  font-size: 1.5rem;
  font-weight: 500;
  cursor: pointer;
`;

export const StyeldkakaoLoginButton = styled.button`
  color: #0e0e0e;
  display: flex;
  align-items: center;
  justify-content: center;
  /* border: 1px solid #d9d9d9; */
  border: none;
  border-radius: 6px;
  height: 45px;
  background-color: #fee500;
  font-size: 1.5rem;
  font-weight: 500;
  cursor: pointer;
`;

export const StyledSvg = styled.svg``;

export const StyledText = styled.p`
  margin-left: ${({ $marginLeft }) => $marginLeft};
  font-size: 1.5rem;
  font-weight: 500;
  color: #0e0e0e;
`;
