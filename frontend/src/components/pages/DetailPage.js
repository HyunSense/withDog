import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import DetailHeader from "../place/DetailHeader";
import DetailMain from "../place/DetailMain";
import Loading from "../common/Loading";
import { getPlace } from "../../apis/place";

function DetailPage() {
  const { id } = useParams();
  const [place, setPlace] = useState(null);

  useEffect(() => {
    const fetchPlaceDetail = async () => {
      try {
        const response = await getPlace(id);

        setPlace(response.data.data);
      } catch (error) {
        console.error("fetching Error = ", error);
      }
    };

    fetchPlaceDetail();
  }, [id]);

  return place ? (
    <div className="pb-10">
      <DetailHeader name={place.name} id={place.id} />
      <DetailMain place={place} />
    </div>
  ) : (
    <Loading />
  );
}

export default DetailPage;
