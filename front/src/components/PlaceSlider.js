import React, { useEffect, useMemo, useState } from "react";
import Slider from "react-slick";
import styled from "styled-components";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import placeTestImg from "../images/placeTestImg.png";
import PlaceItems from "./PlaceItems";
import axios from "axios";

const StyledSlider = styled(Slider)`
  .slick-dots {
    position: static;
  }

  .slick-slide {
    padding-right: 20px;
  }

  .slick-list {
    padding-bottom: 15px;
  }
`;

const StyledPlaceItemList = styled.div`
  display: grid;
  /* place-items: center; */
  
  grid-template-columns: repeat(2, 1fr);
  gap: 20px 20px;
`;

function PlaceSlider({ children }) {
  const settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8080/places");
        setData(response.data);
        console.log("response.data = ", response.data);
      } catch (error) {
        console.error("error = ", error);
      }
    };

    fetchData();
  }, []);

  const slicedDatas = useMemo(() => {
    const slices = [];
    for (let i = 0; i < data.length; i += 4) {
      slices.push(data.slice(i, i + 4));
    }
    console.log("slices = ", slices);
    return slices;
  }, [data]);


  return (
    <StyledSlider {...settings}>
      {slicedDatas.map((items, index) => (
        <div key={index}>
          <StyledPlaceItemList>
            {items.map((i, itemIndex) => (
              <PlaceItems item={i} key={itemIndex} />
            ))}
          </StyledPlaceItemList>
        </div>
      ))}
    </StyledSlider>
  );
}

export default PlaceSlider;
