// import ReactDOM from "react-dom/client";
import ReactDOM from "react-dom";
import { useEffect, useState } from "react";
import { FILTER_OPTIONS } from "../../constants/filters";
import FilterSection from "../searchFilter/FilterSection";
import FilterItem from "../searchFilter/FilterItem";
import { useNavigate } from "react-router-dom";

const SearchModal = ({ onClose }) => {
  const [keyword, setKeyword] = useState("");
  const [selectedFilters, setSelectedFilters] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = "";
    };
  }, []);

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

  const handleSearch = async () => {
    const queryObj = { ...selectedFilters };
    if (keyword.trim()) {
      queryObj.keyword = keyword;
    }
    const queryStr = new URLSearchParams(queryObj).toString();
    navigate(`/search/result?${queryStr}`);
    onClose();
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSearch();
    }
  };

  return ReactDOM.createPortal(
    // scrollbar-width: none 처리
    <div className="flex flex-col fixed inset-0 w-full z-40 bg-gray-50 overflow-hidden">
      <div className="flex flex-col  fixed z-50 left-1/2 -translate-x-1/2 w-full md:w-[720px] mx-auto">
        <div className="h-[calc(var(--vh,1vh)_*_100)] bg-slate-100 pb-24 overflow-y-auto">
          <div className="flex justify-between items-center w-full px-3">
            <div
              className="flex justify-center items-center cursor-pointer w-12 h-12 text-base font-medium"
              onClick={onClose}
            >
              <svg
                width="20"
                height="20"
                viewBox="-0.5 0 25 25"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M3 21.32L21 3.32001"
                  stroke="#3E4243"
                  strokeWidth="1.5"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
                <path
                  d="M3 3.32001L21 21.32"
                  stroke="#3E4243"
                  strokeWidth="1.5"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                />
              </svg>
            </div>
            <span className="text-base font-medium text-neutral-950">검색</span>
            <div className="w-12 h-12"></div>
          </div>
          <div className="flex flex-col gap-4 mt-0.5 mx-4 px-4 py-5 bg-white rounded-lg">
            <span className="text-sm font-medium text-neutral-950">
              견주님, 어떤 장소를 찾으시나요?
            </span>
            <div className="flex w-full gap-2 px-4 py-3 border border-solid border-gray-300 rounded-lg">
              <div className="flex justify-center items-center cursor-pointer">
                <svg
                  width="20"
                  height="20"
                  viewBox="0 0 24 24"
                  fill="#4E5354"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    fillRule="evenodd"
                    clipRule="evenodd"
                    d="M17.9375 10.9375C17.9375 14.8035 14.8035 17.9375 10.9375 17.9375C7.07151 17.9375 3.9375 14.8035 3.9375 10.9375C3.9375 7.07151 7.07151 3.9375 10.9375 3.9375C14.8035 3.9375 17.9375 7.07151 17.9375 10.9375ZM16.4088 17.4427C14.9303 18.6875 13.0215 19.4375 10.9375 19.4375C6.24308 19.4375 2.4375 15.6319 2.4375 10.9375C2.4375 6.24308 6.24308 2.4375 10.9375 2.4375C15.6319 2.4375 19.4375 6.24308 19.4375 10.9375C19.4375 13.0078 18.6973 14.9053 17.4672 16.3797L20.7225 19.6931C21.0128 19.9886 21.0086 20.4635 20.7131 20.7538C20.4176 21.044 19.9428 21.0398 19.6525 20.7444L16.4088 17.4427Z"
                    fill="current"
                  ></path>
                </svg>
              </div>
              <input
                className="w-full border-none outline-none text-zinc-700 text-sm font-medium"
                placeholder="장소를 검색하세요."
                value={keyword}
                onChange={(e) => setKeyword(e.target.value)}
                onKeyDown={handleKeyDown}
              />
            </div>
          </div>
          {/* <div className={`rounded-lg bg-white ` + (mode === "admin" ? "py-5 px-0 my-0 mx-0" : "p-4 my-1 mx-4")}></div> */}
          <div className="rounded-lg bg-white p-4 my-1 mx-4">
            <div className="flex justify-between items-center w-full">
              <span className="text-sm font-medium text-neutral-950">
                필터를 선택해 주세요.
              </span>
              <span
                className="text-sm font-medium cursor-pointer underline text-zinc-700"
                onClick={handleFilterReset}
              >
                초기화
              </span>
            </div>
            {FILTER_OPTIONS.map((filter) => (
              <FilterSection
                key={filter.id}
                title={filter.title}
                multiSelect={filter.multiSelect}
              >
                {filter.options.map((option) => (
                  <FilterItem
                    key={option.value}
                    label={option.label}
                    value={option.value}
                    isSelected={selectedFilters[filter.id]?.includes(
                      option.value
                    )}
                    onSelect={() =>
                      handleFilterSelect(
                        filter.id,
                        option.value,
                        filter.multiSelect
                      )
                    }
                  />
                ))}
              </FilterSection>
            ))}
          </div>
        </div>
        <div className="absolute left-0 bottom-0 w-full p-4 bg-white z-10">
          <button
            className="w-full h-12 rounded-lg text-lg font-semibold text-white bg-cyan-950"
            onClick={handleSearch}
          >
            검색
          </button>
        </div>
      </div>
    </div>,
    document.getElementById("modal-root")
  );
};

export default SearchModal;
