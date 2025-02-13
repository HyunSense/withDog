import {
  StyledSearchFilterBox,
  StyledSearchFilterTypeTitle,
  StyledSearchFilterList,
} from "../../styles/SearchFilter.Styled";

const FilterSection = ({ title, children }) => {
  return (
    <StyledSearchFilterBox>
      <StyledSearchFilterTypeTitle>{title}</StyledSearchFilterTypeTitle>
      <StyledSearchFilterList>{children}</StyledSearchFilterList>
    </StyledSearchFilterBox>
  );
};

export default FilterSection;
