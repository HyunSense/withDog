import React, { useEffect, useState } from "react";
import { getTop3Places } from "../../apis/place";
import PlaceItem from "./PlaceItem";

const PopularList = () => {
  const [top3Places, setTop3Places] = useState([]);

  const popularListTitle = "주간 인기 장소📈";

  useEffect(() => {
    getTop3();
  }, []);

  const getTop3 = async () => {
    try {
      const response = await getTop3Places();

      const top3Data = response.data.data;
      setTop3Places([...top3Data]);
    } catch (error) {
      console.error("getTop3 error = ", error);
    }
  };

  return (
    <section className="px-4 mb-7">
      <p className="text-lg font-bold mb-4">{popularListTitle}</p>
      <div className="flex gap-x-3">
        {top3Places.map((place) => (
          <PlaceItem
            item={place}
            key={place.id}
            perRow={3}
            to={`/places/${place.id}`}
          />
        ))}
      </div>
    </section>
  );
};

export default PopularList;
