import React from "react";
import Slider from "react-slick";

const DetailSlider = ({ imgs }) => {
  const settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
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
    <Slider {...settings}>
      {imgs.map((img) => (
        <div
          className="w-full h-[43vh] outline-none border-none overflow-hidden"
          key={img.imagePosition}
        >
          <img
            className="w-full h-full object-cover"
            src={img.imageUrl}
            alt="장소 상세 이미지"
          />
        </div>
      ))}
    </Slider>
  );
};

export default DetailSlider;
