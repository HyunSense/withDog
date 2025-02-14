import styled from "styled-components";

export const StyledModalOverlay = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgb(243, 246, 246);
  z-index: 1001;
  overflow: hidden;
  scrollbar-width: none;
`;

export const StyledSearchModal = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: fixed;
  z-index: 1002;
  left: 50vw;
  transform: translate(-50%);

  @media (min-width: 720px) {
    width: 720px;
    margin: 0 auto;
  }
`;

export const StyledSearchModalPage = styled.div`
  /* position: relative; */
  /* background-color: #f8fafb; */
  /* width: 100%; */
`;

export const StyledSearchContainer = styled.div`
  height: calc(var(--vh, 1vh) * 100);
  background-color: #f8fafb;
  padding-bottom: 100px;
  overflow-y: auto;
`;

export const StyledSearchFilterHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 10px;
`;

export const StyledSearchHeaderTitle = styled.p`
  font-size: 1.6rem;
  font-weight: 500;
`;

export const StyledSearchCloseBox = styled.div`
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

export const StyledSearchDummyBox = styled.div`
  width: 50px;
  height: 50px;
`;

export const StyledSearchKeywordBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin: 2px 15px 0px 15px;
  padding: 20px 16px;
  background-color: #ffffff;
  border-radius: 8px;
`;

export const StyledSearchKeywordText = styled.p`
  font-size: 1.5rem;
  font-weight: 500;
`;

export const StyledSearchKeywordIconBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  -webkit-box-pack: center;
  -webkit-box-align: center;
`;

export const StyledSearchKeywordInputBox = styled.div`
  width: 100%;
  display: flex;
  gap: 7px;
  padding: 12px 16px;
  border: 1px solid #dde2e3;
  border-radius: 8px;
`;

export const StyledSearchKeywordInput = styled.input`
  width: 100%;
  border: none;
  outline: none;
  color: #4e5354;
  font-size: 1.5rem;
  font-weight: 500;
`;

export const StyledSearchFilterContainer = styled.div`
  margin: 4px 15px;
  padding: 16px 16px;
  border-radius: 8px;
  background-color: #ffffff;
`;

export const StyledSearchFilterTextBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const StyledSearchFilterTitle = styled.p`
  font-size: 1.5rem;
  font-weight: 500;
`;

export const StyledSearchFilterReset = styled.p`
  font-size: 1.3rem;
  font-weight: 500;
  color: #83898c;
  text-decoration: underline;
  cursor: pointer;
`;

export const StyledSearchButtonBox = styled.div`
  width: 100%;
  position: absolute;
  bottom: 0px;
  left: 0px;
  padding: 16px 16px;
  background-color: #ffffff;
  z-index: 2;
`;

export const StyledSearchButton = styled.button`
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

// FilterSection.js
export const StyledSearchFilterBox = styled.div`
  margin-top: 23px;
`;

export const StyledSearchFilterTypeTitle = styled.p`
  display: flex;
  font-weight: 500;
  font-size: 1.4rem;
`;

export const StyledSearchFilterList = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 8px 10px;
  margin-top: 10px;
`;

// FilterItem.js

export const StyledSearchFilterItem = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px 15px;
  border-radius: 30px;
  border: 1px solid #eff2f2;
  background-color: ${({ $isSelected }) =>
    $isSelected ? "#3E4243" : "#f8fafb"};
  color: ${({ $isSelected }) => ($isSelected ? "#ffffff" : "#0e0e0e")};
  cursor: pointer;
`;

export const StyledSearchFilterItemText = styled.p`
  font-weight: 400;
  font-size: 1.3rem;
`;
