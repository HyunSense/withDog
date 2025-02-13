import {
  StyledSearchFilterItem,
  StyledSearchFilterItemText,
} from "../../styles/SearchFilter.Styled";

const FilterItem = ({ label, value, isSelected, onSelect }) => {
  return (
    <StyledSearchFilterItem
      $isSelected={isSelected}
      onClick={() => onSelect(value)}
    >
      <StyledSearchFilterItemText>{label}</StyledSearchFilterItemText>
    </StyledSearchFilterItem>
  );
};

export default FilterItem;
