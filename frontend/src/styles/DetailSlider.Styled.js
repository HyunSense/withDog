import Slider from "react-slick";
import styled from "styled-components";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";


export const StyledDetailSlider = styled(Slider)`
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

export const StyledDetailImgBox = styled.div`
  width: 100%;
  height: 43vh;

  outline: none;
  border: none;
  overflow: hidden;
`;

export const StyledDetailImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;