import React from "react";
import styled from "styled-components";

const StyledAuthMain = styled.main`
  display: flex;
  padding: 0 70px;
  justify-content: center;
`;

function AuthMain({ children }) {
  return <StyledAuthMain>{children}</StyledAuthMain>;
}

export default AuthMain;
