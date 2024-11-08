import React from "react";
import styled, { css } from "styled-components";

const StyledButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  border: 0.5px solid #d9d9d9;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  background-color: #ffffff;
  width: 65px;
  height: 27px;
  font-weight: 600;
  font-family: "Inter", sans-serif;

  ${({ $sm }) =>
    $sm &&
    css`
      font-size: 1.4rem;
    `}
`;

function Button({ children, ...props }) {
  return <StyledButton {...props}>{children}</StyledButton>;
}

export default Button;
