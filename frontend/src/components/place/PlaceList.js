import React, { useEffect, useState } from "react";
import PlaceItem from "./PlaceItem";

const PlaceList = ({ sectionTitle, getPlacesApi, itemSize }) => {
  const [places, setPlaces] = useState([]);

  useEffect(() => {
    fetchPlaces(getPlacesApi, { size: itemSize });
  }, [getPlacesApi, itemSize]);

  const fetchPlaces = async (apiFunc, params) => {
    try {
      const response = await apiFunc(params);
      const responseData = response.data.data;
      setPlaces(responseData);
    } catch (error) {
      console.log(`${apiFunc.name} error = `, error);
    }
  };

  return (
    <section className="px-4 mb-7">
      <p className="text-lg font-bold text-black mb-4">{sectionTitle}</p>
      <div className="flex flex-wrap justify-center gap-x-2 gap-y-5">
        {places.map((place) => (
          <PlaceItem item={place} key={place.id} perRow={2} to={`/places/${place.id}`} />
        ))}
      </div>
    </section>
  );
};

export default PlaceList;
