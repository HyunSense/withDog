import { React, useState } from "react";
import PopularList from "../place/PopularList";
import SearchModalOpener from "../place/SearchModalOpener";
import SearchModal from "../pages/SearchModal";
import PlaceList from "../place/PlaceList";
import Nav from "./Nav";
import { getRandomPlaces, getRecentPlaces } from "../../apis/place";

const Main = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <main>
        <SearchModalOpener onOpen={() => setIsModalOpen(true)} />
        <Nav />
        <PopularList />
        <PlaceList
          sectionTitle={"최근 등록된 장소🌟"}
          getPlacesApi={getRecentPlaces}
          itemSize={4}
        />
        <PlaceList
          sectionTitle={"추천 장소👍"}
          getPlacesApi={getRandomPlaces}
          itemSize={4}
        />
      </main>
      {isModalOpen && <SearchModal onClose={() => setIsModalOpen(false)} />}
    </>
  );
};

export default Main;
