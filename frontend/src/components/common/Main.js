import React from "react";
import styled from "styled-components";
import Popular from "../place/Popular";
import PlaceList from "../place/PlaceList";
import Nav from "./Nav";

const StyledMain = styled.main``;

const Main = () => {
  return (
    <StyledMain>
      <Nav />
      <Popular />
      <PlaceList />
    </StyledMain>
  );
};

export default Main;
