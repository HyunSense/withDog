import React from "react";
import styled from "styled-components";
import Slider from "react-slick";
import testDetailImage from "../images/detail-img.jpg";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const StyledDetailSlider = styled(Slider)`
  .slick-dots {
    position: absolute;
    bottom: 3px;
    z-index: 2;
  }

  .slick-dots li {
    margin: 0 2px;
  }

  .slick-dots li button:before {
    font-size: 1rem;
    color: #d9d9d9;
    opacity: 0.8;
  }

  .slick-dots li.slick-active button:before {
    color: #ffffff;
  }
`;

const StyledDetailImgBox = styled.div`
  width: 100%;
  height: 43vh;

  outline: none;
  border: none;
  overflow: hidden;
`;

const StyledDetailImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const imgDatas = [
  {
    id: 1,
    image: testDetailImage,
  },
  {
    id: 2,
    image: testDetailImage,
  },
  {
    id: 3,
    image: testDetailImage,
  },
  {
    id: 4,
    image: testDetailImage,
  },
  {
    id: 5,
    image: testDetailImage,
  },
];

function DetailSlider({imgs}) {
  const settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    onReInit: () => {
      // slick-slide 내부 자식의 자식 태그에서 tabindex 제거
      const slides = document.querySelectorAll(".slick-slide");
      slides.forEach((slide) => {
        const childTabindex = slide.querySelector("[tabindex]");
        if (childTabindex) {
          childTabindex.removeAttribute("tabindex");
        }
      });
    },
  };

  return (
    <StyledDetailSlider {...settings}>
      {imgs.map((i) => (
        <StyledDetailImgBox key={i.imagePosition}>
          <StyledDetailImg src={i.imageUrl} />
        </StyledDetailImgBox>
      ))}
    </StyledDetailSlider>
  );
}

export default DetailSlider;
