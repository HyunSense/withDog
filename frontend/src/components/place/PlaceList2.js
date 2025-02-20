import React, { useEffect, useState } from "react";
import * as S from "../../styles/PlaceList.Styled";
import PlaceItems from "./PlaceItems";

const PlaceList2 = ({ sectionTitle, getPlacesApi, itemSize }) => {
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
    <S.StyledPlaceList>
      <S.StyledPlaceListTitleBox>
        <S.StyledTitleText>{sectionTitle}</S.StyledTitleText>
        {/* subTitle 필요 */}
      </S.StyledPlaceListTitleBox>
      <S.StyledPlaceItemList>
        {places.map((place) => (
          <PlaceItems
            item={place}
            key={place.id}
            to={`/places/${place.category}/${place.id}`}
          />
        ))}
      </S.StyledPlaceItemList>
    </S.StyledPlaceList>
  );
};

export default PlaceList2;
