import { Link } from "react-router-dom";
import styled from "styled-components";

export const StyledItem = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const StyledItemInfo = styled(Link)`
  display: flex;
  width: 100%;
  align-items: center;
  gap: 0 10px;
`;

export const StyledItemTextBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px 0;
`;

export const StyledPlaceText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
`;

export const StyledAddressText = styled.p`
  font-size: 1.3rem;
  font-weight: 500;
  color: #83898c;
`;

export const StyledItemThumbnailBox = styled.div`
  width: 48px;
  height: 48px;
  
`;

export const StyledItemThumbnail = styled.img`
  /* width: 48px; */
  /* height: 48px; */
  width: 100%;
  height: 100%;
  border: 0.5px solid #dde2e3;
  border-radius: 50%;
  object-fit: cover; // ?
`;

export const StyledItemUpdateBox = styled.div`
  display: flex;
  align-items: center;
  gap: 0 15px;
`;

export const StyledItemEditButton = styled.button`
  border: none;
  background: none;
  width: 35px;
  font-size: 1.4rem;
  font-weight: 500;
  color: #007aff;
  cursor: pointer;
`;

export const StyledItemRemoveCheckBox = styled.input`
  display: none;

  &:checked + label svg {
    /* fill: #4caf50; */
    fill: #61d377;
  }
`;

export const StyledItemRemoveCheckBoxLabel = styled.label`
  pointer-events: auto;
`;

export const StyledItemRemoveCheckBoxSvg = styled.svg`
  display: flex;
  cursor: pointer;
  align-items: center;
`;