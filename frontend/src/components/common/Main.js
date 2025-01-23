import React from "react";
import styled from "styled-components";
import Nav from "./Nav";
import Popular from "../place/Popular";
import PlaceList from "../place/PlaceList";
import Search from "../place/Search";

const StyledMain = styled.main``;

// const Main = ({ showBookmark }) => {
const Main = () => {
  return (
    <StyledMain>
      {/* <Search /> */}
      <Nav />
      {/* {showBookmark ? (
        <Bookmark /> // 북마크 랜더링
      ) : (
        <>
          <Popular />
          <PlaceList />
        </>
      )} */}
      <Popular />
      <PlaceList />
    </StyledMain>
  );
};

export default Main;
