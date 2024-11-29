import styled from "styled-components";

export const StyledRegisterImageUpload = styled.div`
  margin-top: 5px;
  margin-bottom: 20px;
`;

export const StyledImageBox = styled.div`
  display: flex;
  flex-direction: column;
  height: 130px;
  border: 0.4px solid #dde2e3;
  border-radius: 8px;
  resize: none;
  padding: 5px 7px;
  overflow-y: auto; // ??
`;

export const StyledImage = styled.div`
  font-size: 1.5rem;
  padding: 1px;
  cursor: pointer;
  background-color: ${({ selected }) => (selected ? "#e0e0e0" : "#ffffff")};
`;

export const StyledImageControlBox = styled.div`
  margin-top: 5px;
  display: flex;
  justify-content: end;
  align-items: center;
  gap: 0 8px;
`;
export const StyledImageControlIcon = styled.img`
  width: ${({ width }) => width || "25px"};
  height: ${({ height }) => height || "25px"};
  margin: ${({ margin }) => margin};
  rotate: ${({ rotate }) => rotate};
  cursor: pointer;
`;

export const StyledFileInput = styled.input`
  display: none;
`;