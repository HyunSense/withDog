import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import DetailHeader from "../place/DetailHeader";
import DetailMain from "../place/DetailMain";
import styled from "styled-components";
import Loading from "../common/Loading";
import { getPlace } from "../../apis/place";

const StyledDetailPageContainer = styled.div`
  padding-bottom: 80px;
`;

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
    <StyledDetailPageContainer>
      <DetailHeader name={place.name} id={place.id} />
      <DetailMain place={place} />
    </StyledDetailPageContainer>
  ) : (
    <Loading />
  );
}

export default DetailPage;
