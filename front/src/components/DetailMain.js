import React, { useEffect, useState } from "react";
import styled from "styled-components";
import detailImg from "../images/detail-img.jpg";
import DetailSlider from "./DetailSlider";
import StyledText from "./StyledText";
import { Link, useParams } from "react-router-dom";
import axios from "axios";

const StyledDetailMain = styled.main`
  /* padding: 20px 30px 0 30px; */
  padding: 20px 0 0 0;
`;

const StyledDetailImgBox = styled.div`
  height: 400px;
`;

const StyledDetailImg = styled.img`
  width: 100%;
  height: 100%;
`;

const StyledDetailInfoBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  gap: 5px 0;
  padding: 0 30px;
`;

const StyledDetailInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0 10px;
  margin: ${({ $margin }) => $margin};
`;

const StyledDetailReservationLink = styled.a`
  display: block;
  margin-top: 20px;
  outline: none;
  text-decoration: none;
  padding: 0 30px;
  cursor: pointer;
`;

const StyledDetailInfoBlogBox = styled.div`
  margin-top: 10px;
  padding: 20px 30px;
`;

const StyledDetailInfoBlogList = styled.div`
  margin-top: 15px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px 20px;
`;

const StyledDetailInfoBlogLink = styled.a`
  outline: none;
  text-decoration: none;
  color: black;
  cursor: pointer;
`;

const StyledDetailInfoBlogImgBox = styled.div`
  width: 100%;
  position: relative;
  padding-top: 100%;
  overflow: hidden;
`;

const StyledDetailInfoBlogImgTextBox = styled.div`
  position: absolute;
  width: 100%;
  padding: 10px 10px;
  bottom: 0;
  left: 0;
  background-color: rgba(217, 217, 217, 0.5);
  border-radius: 0 0 8px 8px;
  /* opacity: 50%; */
`;

const StyledDetailInfoBlogImg = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
`;

const StyledDetailTextLink = styled.a`
  outline: none;
  text-decoration: none;
  cursor: pointer;
`;

const StyledBlogTitleText = styled.p`
  font-size: 1.5rem;
  font-weight: 700;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`

const StyledBlogDescriptionText = styled.p`
  font-size: 1.2rem;
  color: rgba(55, 53, 47, 0.85);
  height: 3.5rem;
  overflow: hidden;
`;

function DetailMain({ place }) {
  const handleCopyClipBoard = (text) => {
    try {
      navigator.clipboard.writeText(text);
      alert("클립보드에 복사되었습니다.");
    } catch (error) {
      alert("클립보드 복사에 실패하였습니다.");
    }
  };

  return (
    <StyledDetailMain>
      <DetailSlider imgs={place.placeImages} />
      <StyledDetailInfoBox>
        <StyledDetailInfo $margin="0 0 10px 0">
          <StyledText fontSize="2rem" fontWeight="700">
            {place.name}
          </StyledText>
        </StyledDetailInfo>
        <StyledDetailInfo>
          <StyledText fontSize="1.5rem">{place.address}</StyledText>
          <StyledDetailInfo>
            <StyledDetailTextLink>
              <StyledText
                fontSize="1.5rem"
                onClick={() => handleCopyClipBoard(place.address)}
              >
                복사하기
              </StyledText>
            </StyledDetailTextLink>
            <StyledDetailTextLink
              href={`https://map.naver.com/p/search/${place.address}`}
              target="_blank"
              rel="noopener noreferrer"
            >
              <StyledText fontSize="1.5rem">지도보기</StyledText>
            </StyledDetailTextLink>
          </StyledDetailInfo>
        </StyledDetailInfo>
        <StyledDetailInfo>
          <StyledText fontSize="1.5rem">{place.phone}</StyledText>
          <StyledDetailTextLink href={`tel:${place.phone}`}>
            <StyledText fontSize="1.5rem">전화하기</StyledText>
          </StyledDetailTextLink>
        </StyledDetailInfo>
        <StyledDetailInfo>
          <StyledText fontSize="1.5rem">
            {place.price}
            <span>원 ~</span>
          </StyledText>
        </StyledDetailInfo>
      </StyledDetailInfoBox>
      <StyledDetailReservationLink
        href={place.reservationLink}
        target="_blank"
        rel="noopener noreferrer"
      >
        <StyledText fontSize="1.5rem" fontWeight="700">
          예약하기
        </StyledText>
      </StyledDetailReservationLink>
      <StyledDetailInfoBlogBox>
        <StyledText fontSize="1.5rem">관련 블로그</StyledText>
        <StyledDetailInfoBlogList>
          {place.placeBlogs.map((blog, index) => (
            <StyledDetailInfoBlogLink
              href={blog.blogUrl}
              target="_blank"
              rel="noopener noreferrer"
              key={index}
            >
              <StyledDetailInfoBlogImgBox>
                <StyledDetailInfoBlogImg referrerPolicy="same-origin" src={blog.imageUrl} />
                <StyledDetailInfoBlogImgTextBox>
                  <StyledBlogTitleText fontSize="1.5rem" fontWeight="700">
                    {blog.title}
                  </StyledBlogTitleText>
                  {/* <StyledText fontSize="1.2rem">{blog.description}</StyledText> */}
                  <StyledBlogDescriptionText fontSize="1.2rem">{blog.description}</StyledBlogDescriptionText>
                </StyledDetailInfoBlogImgTextBox>
              </StyledDetailInfoBlogImgBox>
            </StyledDetailInfoBlogLink>
          ))}
        </StyledDetailInfoBlogList>
      </StyledDetailInfoBlogBox>
    </StyledDetailMain>
  );
}

export default DetailMain;
