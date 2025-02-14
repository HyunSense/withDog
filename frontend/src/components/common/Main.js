import React, { useState } from "react";
import styled from "styled-components";
import Popular from "../place/Popular";
import PlaceList from "../place/PlaceList";
import SearchFake from "../place/SearchFake";
import SearchModal from "../pages/SearchModal";

const StyledMain = styled.main``;

const Main = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <StyledMain>
        <SearchFake onOpen={() => setIsModalOpen(true)} />
        <Popular />
        <PlaceList />
      </StyledMain>
      {isModalOpen && <SearchModal onClose={() => setIsModalOpen(false)} />};
    </>
  );
};

export default Main;
