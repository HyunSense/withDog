import React, { useState } from "react";
import styled from "styled-components";
import Popular from "../place/Popular";
import PlaceList from "../place/PlaceList";
import Nav from "./Nav";
import Search from "../place/Search";
import SearchModal from "../pages/SearchModal";

const StyledMain = styled.main``;

const Main = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <StyledMain>
        <Search onOpen={() => setIsModalOpen(true)}/>
        {/* <Nav /> */}
        <Popular />
        <PlaceList />
      </StyledMain>
      {isModalOpen && <SearchModal onClose={() => setIsModalOpen(false)} />};
    </>
  );
};

export default Main;
