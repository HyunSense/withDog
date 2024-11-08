import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";


const StyledBackButton = styled.button`
    border: none;
    outline: none;
    background: none;
    cursor: pointer;
`;

function BackButton({ children }) {
  const navigate = useNavigate();

  return <StyledBackButton onClick={() => navigate(-1)}>{children}</StyledBackButton>;
}

export default BackButton;
