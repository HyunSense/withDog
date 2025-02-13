import styled from "styled-components";
import PrevButton from "../common/PrevButton";

const StyledSearchHeader = styled.header`
  position: sticky;
  top: 0;
  z-index: 1000;
  width: 100%;
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 13px 0 13px;
  /* margin-bottom: 20px; */
  background-color: #ffffff;
`

const StyledPrevButtonBox = styled.div`
  width: 25px;
  height: 25px;
  margin-right: 10px;
`;

const StyledBalanceDummy = styled.div`
  width: 25px;
  height: 25px;
  margin-left: 10px;
`;



const StyledTitleText = styled.p`
  font-size: 2rem;
  font-weight: 600;
  color: #000000;
`;



const SearchHeader = () => {
  return (
    <StyledSearchHeader>
        <StyledPrevButtonBox>
            <PrevButton to="/" />
        </StyledPrevButtonBox>
        <StyledTitleText>검색</StyledTitleText>
        <StyledBalanceDummy></StyledBalanceDummy>
    </StyledSearchHeader>
  );
};

export default SearchHeader;
