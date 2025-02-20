import { useState } from "react";
import * as S from "../../styles/SearchFilter.Styled";
import { FILTER_OPTIONS } from "../../constants/filters";
import FilterSection from "./FilterSection";
import FilterItem from "./FilterItem";

const FilterContainer = ({ mode = "search", selectedFilters, setSelectedFilters}) => {
//   const [selectedFilters, setSelectedFilters] = useState({});

  const handleFilterSelect = (filterId, value, multiSelect) => {
    setSelectedFilters((prev) => {
      const currentValues = prev[filterId] || [];

      if (multiSelect) {
        return {
          ...prev,
          [filterId]: currentValues.includes(value)
            ? currentValues.filter((v) => v !== value)
            : [...currentValues, value],
        };
      }

      return {
        ...prev,
        [filterId]: currentValues.includes(value) ? [] : [value],
      };
    });
  };

  const handleFilterReset = () => {
    setSelectedFilters({});
  };

  return (
    <S.StyledSearchFilterContainer mode={mode}>
      <S.StyledSearchFilterTextBox>
        <S.StyledSearchFilterTitle>
          {mode === "search"
            ? "필터를 선택해 주세요."
            : "등록 필터를 설정하세요."}
        </S.StyledSearchFilterTitle>
        <S.StyledSearchFilterReset onClick={handleFilterReset}>
          초기화
        </S.StyledSearchFilterReset>
      </S.StyledSearchFilterTextBox>
      {FILTER_OPTIONS.map((filter) => (
        <FilterSection key={filter.id} title={filter.title}>
          {filter.options.map((option) => (
            <FilterItem
              key={option.value}
              label={option.label}
              value={option.value}
              isSelected={selectedFilters[filter.id]?.includes(option.value)}
              onSelect={() =>
                handleFilterSelect(
                  filter.id,
                  option.value,
                  mode === "search"
                    ? filter.multiSelect
                    : filter.adminMultiSelect
                )
              }
            />
          ))}
        </FilterSection>
      ))}
    </S.StyledSearchFilterContainer>
  );
};

export default FilterContainer;
