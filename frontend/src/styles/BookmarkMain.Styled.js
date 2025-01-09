import styled from "styled-components";

export const StyledBookmarkMain = styled.main`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  padding: 20px 16px 0 16px;
  flex: 1 0 0px;
  overflow-y: auto;
  ::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none;
`;

export const StyledTitleText = styled.p`
  font-size: 1.5rem;
  font-weight: 600;
  /* margin-bottom: 20px; */
  color: #000000;
`;

export const StyledBookmarkList = styled.div`
  width: 100%;
  height: 100%;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 25px 0;
  background-color: #ffffff;
`;

export const StyledBookmarkItem = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const StyledBookmarkItemInfo = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  gap: 0 10px;
`;

export const StyledBookmarkItemTextBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px 0;
`;

export const StyledBookmarkPlaceText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
  color: #000000;
`;

export const StyledBookmarkAddressText = styled.p`
  font-size: 1.3rem;
  font-weight: 500;
  color: #83898c;
`;

export const StyledBookmarkThumbnail = styled.img`
  width: 48px;
  height: 48px;
  border: 0.5px solid #dde2e3;
  border-radius: 50%;

  object-fit: cover;
`;

export const StyledBookmarkCheckBox = styled.input`
  display: none;

  &:checked + label svg {
    fill: #61d377;
  }
`;

export const StyledBookmarkCheckBoxLabel = styled.label`
  pointer-events: auto;
  cursor: pointer;
`;

export const StyledItemRemoveCheckBoxSvg = styled.svg`
  display: flex;
  cursor: pointer;
  align-items: center;
`;

export const StyledBookmarkIconBox = styled.div`
  width: 25px;
  height: 25px;
  border: none;
  background: none;
`;

export const StyledBookmarkIcon = styled.img`
  width: 100%;
  height: 100%;
`;

export const StyledBookmarkRemoveButtonBox = styled.div`
  position: sticky;
  bottom: 0px;
  width: 100%;
  padding: 12px 16px 35px 16px;
  border-top: 1px solid #eff2f2;
  background-color: #ffffff;
`;

export const StyledBookmarkRemoveButton = styled.button`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  border: none;
  border-radius: 8px;
  font-size: 1.5rem;
  font-weight: 600;
  height: 48px;
  color: #ffffff;
  background-color: ${({ $isActive }) => ($isActive ? "#022733" : "#dde2e3")};
  cursor: ${({ $isActive }) => $isActive && "pointer"};
`;