import React from "react";
import * as S from "../../styles/DetailSlider.Styled";

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
    <S.StyledDetailSlider {...settings}>
      {imgs.map((i) => (
        <S.StyledDetailImgBox key={i.imagePosition}>
          {/* <S.StyledDetailImg src={`http://192.168.0.5:8080${i.imageUrl}`} /> */}
          <S.StyledDetailImg src={i.imageUrl} alt="이미지"/>
        </S.StyledDetailImgBox>
      ))}
    </S.StyledDetailSlider>
  );
};

export default DetailSlider;
