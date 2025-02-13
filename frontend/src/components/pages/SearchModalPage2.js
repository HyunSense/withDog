import { useState } from "react";
import styled from "styled-components";

const StyledSearchModalPage = styled.div`
  position: relative;
  width: 100%;
`;

const StyledSearchInputFilterContainer = styled.div`
  height: calc(var(--vh, 1vh) * 100);
  background-color: #f8fafb;
  padding-bottom: 100px;
  overflow-y: auto;
`;

const StyledSearchKeywordBox = styled.div`
  margin: 10px 15px 0px 15px;
  padding: 10px 16px;
  background-color: #ffffff;
  border-radius: 8px;
`;

const StyledSearchKeywordIconBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  -webkit-box-pack: center;
  -webkit-box-align: center;
`;

const StyledSearchKeywordInputBox = styled.div`
  width: 100%;
  display: flex;
  gap: 7px;
  padding: 12px 16px;
  border: 1px solid #dde2e3;
  border-radius: 8px;
`;

const StyledSearchKeywordInput = styled.input`
  width: 100%;
  border: none;
  outline: none;
  color: #4e5354;
  font-size: 1.5rem;
  font-weight: 500;
`;

const StyledSearchFilterContainer = styled.div`
  margin: 4px 15px;
  padding: 16px 16px;
  border-radius: 8px;
  background-color: #ffffff;
`;

const StyledSearchFilterTitle = styled.p`
  font-size: 1.5rem;
  font-weight: 500;
`;

const StyledSearchFilterBox = styled.div`
  margin-top: 23px;
`;

const StyledSearchFilterTypeTitle = styled.p`
  display: flex;
  font-weight: 500;
  font-size: 1.4rem;
`;

const StyledSearchFilterList = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 8px 10px;
  margin-top: 10px;
`;

const StyledSearchFilterItem = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 15px;
  border-radius: 30px;
  border: 1px solid #eff2f2;
  background-color: #f8fafb;
`;

const StyledSearchFilterItemText = styled.p`
  font-weight: 400;
  font-size: 1.3rem;
`;

const StyledSearchButtonBox = styled.div`
  width: 100%;
  position: absolute;
  bottom: 0px;
  left: 0px;
  padding: 16px 16px;
  background-color: #ffffff;
  z-index: 2;
`;

const StyledSearchButton = styled.button`
  height: 52px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  border-radius: 8px;
  background-color: #022733;
  color: #ffffff;
  font-size: 1.7rem;
  font-weight: 600;
  cursor: pointer;
`;


const SearchModalPage = () => {

    const [selectedFilter, setSelectedFilter] = useState();


  return (
    <StyledSearchModalPage>
      <StyledSearchInputFilterContainer>
        <StyledSearchKeywordBox>
          <StyledSearchKeywordInputBox>
            <StyledSearchKeywordIconBox>
              <svg
                width="20"
                height="20"
                viewBox="0 0 24 24"
                fill="#4E5354"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M17.9375 10.9375C17.9375 14.8035 14.8035 17.9375 10.9375 17.9375C7.07151 17.9375 3.9375 14.8035 3.9375 10.9375C3.9375 7.07151 7.07151 3.9375 10.9375 3.9375C14.8035 3.9375 17.9375 7.07151 17.9375 10.9375ZM16.4088 17.4427C14.9303 18.6875 13.0215 19.4375 10.9375 19.4375C6.24308 19.4375 2.4375 15.6319 2.4375 10.9375C2.4375 6.24308 6.24308 2.4375 10.9375 2.4375C15.6319 2.4375 19.4375 6.24308 19.4375 10.9375C19.4375 13.0078 18.6973 14.9053 17.4672 16.3797L20.7225 19.6931C21.0128 19.9886 21.0086 20.4635 20.7131 20.7538C20.4176 21.044 19.9428 21.0398 19.6525 20.7444L16.4088 17.4427Z"
                  fill="current"
                ></path>
              </svg>
            </StyledSearchKeywordIconBox>
            <StyledSearchKeywordInput placeholder="장소를 검색하세요." />
          </StyledSearchKeywordInputBox>
        </StyledSearchKeywordBox>
        <StyledSearchFilterContainer>
          <StyledSearchFilterTitle>
            필터를 선택해 주세요
          </StyledSearchFilterTitle>
          <StyledSearchFilterBox>
            <StyledSearchFilterTypeTitle>장소 유형</StyledSearchFilterTypeTitle>
            <StyledSearchFilterList>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>캠핑</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>공원</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>펜션</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>카페</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>음식점</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
            </StyledSearchFilterList>
          </StyledSearchFilterBox>
          <StyledSearchFilterBox>
            <StyledSearchFilterTypeTitle>지역</StyledSearchFilterTypeTitle>
            <StyledSearchFilterList>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>서울</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>부산</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>대구</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>인천</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>광주</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>대전</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>울산</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>세종</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>경기</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>강원</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>충북</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>충남</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>전북</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>전남</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>경북</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>경남</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>제주</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
            </StyledSearchFilterList>
          </StyledSearchFilterBox>
          <StyledSearchFilterBox>
            <StyledSearchFilterTypeTitle>
              반려견 이용 유형
            </StyledSearchFilterTypeTitle>
            <StyledSearchFilterList>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>
                  반려견 동반
                </StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>
                  반려견 전용
                </StyledSearchFilterItemText>
              </StyledSearchFilterItem>
            </StyledSearchFilterList>
          </StyledSearchFilterBox>
          <StyledSearchFilterBox>
            <StyledSearchFilterTypeTitle>
              허용견 크기
            </StyledSearchFilterTypeTitle>
            <StyledSearchFilterList>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>소형견</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>중형견</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>대형견</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
            </StyledSearchFilterList>
          </StyledSearchFilterBox>
          <StyledSearchFilterBox>
            <StyledSearchFilterTypeTitle>편의 시설</StyledSearchFilterTypeTitle>
            <StyledSearchFilterList>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>
                  전용 놀이터
                </StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>주차장</StyledSearchFilterItemText>
              </StyledSearchFilterItem>
              <StyledSearchFilterItem>
                <StyledSearchFilterItemText>
                  야외 시설 보유
                </StyledSearchFilterItemText>
              </StyledSearchFilterItem>
            </StyledSearchFilterList>
          </StyledSearchFilterBox>
        </StyledSearchFilterContainer>
      </StyledSearchInputFilterContainer>
      <StyledSearchButtonBox>
        <StyledSearchButton>검색</StyledSearchButton>
      </StyledSearchButtonBox>
    </StyledSearchModalPage>
  );
};

export default SearchModalPage;
