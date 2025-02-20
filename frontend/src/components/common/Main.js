import React, { useState } from "react";
import styled from "styled-components";
import Popular from "../place/Popular";
import PlaceList from "../place/PlaceList";
import SearchFake from "../place/SearchFake";
import SearchModal from "../pages/SearchModal";
import PlaceList2 from "../place/PlaceList2";
import Nav2 from "./Nav2";
import { getRecentPlaces } from "../../apis/place";

const StyledMain = styled.main``;

const Main = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <StyledMain>
        <SearchFake onOpen={() => setIsModalOpen(true)} />
        <Nav2 />
        <Popular />
        {/* <PlaceList /> */}
        <PlaceList2 sectionTitle={"최근 등록된 장소🌟"} getPlacesApi={getRecentPlaces} itemSize={4}/>
        <PlaceList2 sectionTitle={"추천 장소👍"} getPlacesApi={getRecentPlaces} itemSize={4}/>
      </StyledMain>
      {isModalOpen && <SearchModal onClose={() => setIsModalOpen(false)} />}
    </>
  );
};

export default Main;
