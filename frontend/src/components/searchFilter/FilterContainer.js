import { useState } from "react";
import { FILTER_OPTIONS } from "../../constants/filters";
import FilterSection from "./FilterSection";
import FilterItem from "./FilterItem";

const FilterContainer = ({
  mode = "search",
  selectedFilters,
  setSelectedFilters,
}) => {
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
    <div
      className={`rounded-lg bg-white ${
        mode === "admin" ? "py-5 px-0 m-0" : "p-4 mt-1 mx-4 mb-1"
      }`}
    >
      <div className="flex justify-between items-center w-full">
        <span className="font-medium">
          {mode === "search"
            ? "필터를 선택해 주세요."
            : "등록 필터를 설정하세요."}
        </span>
        <span
          className="text-sm font-medium text-zinc-700 cursor-pointer underline"
          onClick={handleFilterReset}
        >
          초기화
        </span>
      </div>
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
    </div>
  );
};

export default FilterContainer;
