import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import DetailHeader from "./DetailHeader";
import DetailMain from "./DetailMain";

function DetailPage() {
  const { id } = useParams();
  const [place, setPlace] = useState(null);

  useEffect(() => {
    const fetchPlaceDetail = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/places/${id}`);

        console.log("response.data = ", response.data.data);
        setPlace(response.data.data);
      } catch (error) {
        console.error("fetching Error = ", error);
      }
    };

    fetchPlaceDetail();
  }, [id]);

  return place ? (
    <>
      <DetailHeader name={place.name} />
      <DetailMain place={place} />
    </>
  ) : (
    <div>Lodaing...</div>
  );
}

export default DetailPage;
