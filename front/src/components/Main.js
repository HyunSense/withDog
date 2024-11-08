import React from "react";
import styled from "styled-components";
import Nav from "./Nav";
import Popular from "./Popular";
import PlaceList from "./PlaceList";

const StyledMain = styled.main``;

function Main() {
  return (
    <StyledMain>
      <Nav />
      <Popular />
      <PlaceList />
    </StyledMain>
  );
}

export default Main;
