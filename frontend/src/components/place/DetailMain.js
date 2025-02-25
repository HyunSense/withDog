import React from "react";
import DetailSlider from "./DetailSlider";
import * as S from "../../styles/DetailMain.Styled";
import { PriceDisplay } from "./PriceDisplay";

const handleCopyClipBoard = (text) => {
  try {
    navigator.clipboard.writeText(text);
    alert("클립보드에 복사되었습니다.");
  } catch (error) {
    alert("클립보드 복사에 실패하였습니다.");
  }
};

const DetailMain = ({ place }) => {
  const fullAddress = `${place.addressPart1} ${place.addressPart2}`.trim();

  return (
    <S.StyledDetailMain>
      <DetailSlider imgs={place.placeImages} />
      <S.StyledDetailInfoBox>
        <S.StyledDetailInfo $margin="0 0 10px 0">
          <S.StyledNameText>{place.name}</S.StyledNameText>
        </S.StyledDetailInfo>
        <S.StyledDetailInfo>
          <S.StyledText $whiteSpace="normal">{fullAddress}</S.StyledText>
          <S.StyledDetailInfo>
            <S.StyledDetailTextLink>
              <S.StyledActionText
                onClick={() => handleCopyClipBoard(fullAddress)}
              >
                복사하기
              </S.StyledActionText>
            </S.StyledDetailTextLink>
            <S.StyledDetailTextLink
              href={`https://map.naver.com/p/search/${fullAddress}`}
              target="_blank"
              rel="noopener noreferrer"
            >
              <S.StyledActionText>지도보기</S.StyledActionText>
            </S.StyledDetailTextLink>
          </S.StyledDetailInfo>
        </S.StyledDetailInfo>
        <S.StyledDetailInfo>
          <S.StyledText>{place.phone}</S.StyledText>
          {place.phone && (
            <S.StyledDetailTextLink href={`tel:${place.phone}`}>
              <S.StyledActionText>전화하기</S.StyledActionText>
            </S.StyledDetailTextLink>
          )}
        </S.StyledDetailInfo>
        <S.StyledDetailInfo>
          <S.StyledPriceText>
            <PriceDisplay price={place.price} />
          </S.StyledPriceText>
        </S.StyledDetailInfo>
      </S.StyledDetailInfoBox>
      {place.reservationUrl && (
        <S.StyledDetailReservationLink
          href={place.reservationUrl}
          target="_blank"
          rel="noopener noreferrer"
        >
          <S.StyledActionText>예약하기</S.StyledActionText>
        </S.StyledDetailReservationLink>
      )}
      <S.StyledDetailInfoBlogBox>
        <S.StyledText>관련 블로그</S.StyledText>
        <S.StyledDetailInfoBlogList>
          {place.placeBlogs.map((blog, index) => (
            <S.StyledDetailInfoBlogLink
              href={blog.blogUrl}
              target="_blank"
              rel="noopener noreferrer"
              key={index}
            >
              <S.StyledDetailInfoBlogImgBox>
                <S.StyledDetailInfoBlogImg
                  referrerPolicy="no-referrer"
                  src={blog.imageUrl}
                />
                <S.StyledDetailInfoBlogImgTextBox>
                  <S.StyledBlogTitleText>{blog.title}</S.StyledBlogTitleText>
                  <S.StyledBlogDescriptionText>
                    {blog.description}
                  </S.StyledBlogDescriptionText>
                </S.StyledDetailInfoBlogImgTextBox>
              </S.StyledDetailInfoBlogImgBox>
            </S.StyledDetailInfoBlogLink>
          ))}
        </S.StyledDetailInfoBlogList>
      </S.StyledDetailInfoBlogBox>
    </S.StyledDetailMain>
  );
};

export default DetailMain;
