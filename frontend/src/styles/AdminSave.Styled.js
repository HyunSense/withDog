import styled from "styled-components";

export const StyledAdminSave = styled.main`
  display: flex;
  /* margin-top: 50px; */ //수정 전 코드
  justify-content: center;
  padding: 0 15px;
  padding-bottom: 80px; // 수정 후 코드
  background-color: #ffffff; // 수정 후 코드
`;

export const StyledSaveForm = styled.form`
  display: flex;
  flex-direction: column;
  /* width: 70%; */
  width: 100%;
  gap: 8px 0;
`;

export const StyledCategoryBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const StyledCategoryButtonBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const StyledCategoryButton = styled.button`
  font-size: 1.5rem;
  padding: 3px 10px;
  cursor: pointer;
  border: 0.4px solid #dde2e3;
  border-radius: ${({ $borderRadius }) => $borderRadius};
  border-left: ${({ $borderLeft }) => $borderLeft};
  background-color: ${({ $isSelected }) =>
    $isSelected ? "#000000" : "#ffffff"};
  color: ${({ $isSelected }) => ($isSelected ? "#ffffff" : "#000000")};
`;

export const StyledInputBox = styled.div`
  display: flex;
  justify-content: space-between;
  flex-direction: ${({ direction }) => direction || "row"};
  align-items: ${({ $alignItems }) => $alignItems || "center"};
  gap: 5px 0;
`;

export const StyledInput = styled.input`
  width: ${({ width }) => width || "80%"};
  /* width: ${({ width }) => width || "65%"}; */
  height: 25px;
  border: 0.4px solid #dde2e3;
  border-radius: 5px;
  font-size: 1.5rem;
  padding: 0 8px;

  &:-webkit-autofill {
    background-color: none !important;
    -webkit-box-shadow: 0 0 0px 1000px #fff inset !important;
  }

  &:-moz-autofill {
    background-color: none !important;
    box-shadow: 0 0 0px 1000px #fff inset !important;
  }

  &[type="number"]::-webkit-inner-spin-button,
  &[type="number"]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  /* Firefox의 숫자 input에서 화살표 없애기 */
  &[type="number"] {
    -moz-appearance: textfield;
    appearance: textfield;
  }
`;

export const StyledAddressInputBox = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 80%;
  align-items: center;
  gap: 5px;
`;

export const StyledImageUploadBox = styled.div``;

export const StyeldRegisterButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2rem;
  font-weight: 700;
  border: 0.4px solid #dde2e3;
  border-radius: 5px;
  background-color: #ffffff;
  margin-top: ${({ $marginTop }) => $marginTop};
  height: 40px;
  cursor: pointer;
`;

export const StyledText = styled.p`
  font-size: 1.5rem;
  font-weight: 400;
`;
