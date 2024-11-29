import React from "react";
import styled from "styled-components";

const StyledContainer = styled.div`
  width: 100%;
  min-height: calc(var(--vh, 1vh) * 100);

  display: flex;
  flex-direction: column;
  background-color: rgb(255, 255, 255);

  @media (min-width: 720px) {
    width: 720px;
    margin: 0 auto;
  }
`;

const Container = ({ children }) => {
  return <StyledContainer>{children}</StyledContainer>;
};

export default Container;
