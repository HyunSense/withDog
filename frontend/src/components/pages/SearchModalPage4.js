import styled from "styled-components";
import SearchFilterHeader from "../searchFilter/SearchFilterHeader";
import SearchFilterMain from "../searchFilter/SearchFilterMain";

const StyledSearchModalPage = styled.div`
  position: relative;
  width: 100%;
  background-color: #f8fafb;

  /* height: calc(var(--vh, 1vh) * 100); */
  /* padding-bottom: 100px; */
  /* overflow-y: auto; */
`;

const SearchModalPage = () => {
  return (
    <StyledSearchModalPage>
      <SearchFilterHeader />
      <SearchFilterMain />
    </StyledSearchModalPage>
  );
};

export default SearchModalPage;
