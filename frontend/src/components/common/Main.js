import React, { useState } from "react";
import styled from "styled-components";
import Popular from "../place/Popular";
import SearchFake from "../place/SearchFake";
import SearchModal from "../pages/SearchModal";
import PlaceList from "../place/PlaceList";
import Nav from "./Nav";
import { getRandomPlaces, getRecentPlaces } from "../../apis/place";

const StyledMain = styled.main``;

const Main = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <StyledMain>
        <SearchFake onOpen={() => setIsModalOpen(true)} />
        <Nav />
        <Popular />
        <PlaceList sectionTitle={"최근 등록된 장소🌟"} getPlacesApi={getRecentPlaces} itemSize={4}/>
        <PlaceList sectionTitle={"추천 장소👍"} getPlacesApi={getRandomPlaces} itemSize={4}/>
      </StyledMain>
      {isModalOpen && <SearchModal onClose={() => setIsModalOpen(false)} />}
    </>
  );
};

export default Main;
