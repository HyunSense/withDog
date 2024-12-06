import React, { useContext, useEffect } from "react";

import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import { AdminContext } from "./AdminContextProvider";
import { getPlace } from "../apis/place";

const StyledItem = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const StyledItemInfo = styled(Link)`
  display: flex;
  width: 100%;
  align-items: center;
  gap: 0 10px;
`;

const StyledItemTextBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px 0;
`;

const StyledPlaceText = styled.p`
  font-size: 1.4rem;
  font-weight: 500;
`;

const StyledAddressText = styled.p`
  font-size: 1.3rem;
  font-weight: 500;
  color: #83898c;
`;

const StyledItemThumbnailBox = styled.div`
  width: 48px;
  height: 48px;
  
`;

const StyledItemThumbnail = styled.img`
  /* width: 48px; */
  /* height: 48px; */
  width: 100%;
  height: 100%;
  border: 0.5px solid #dde2e3;
  border-radius: 50%;
  object-fit: cover; // ?
`;

const StyledItemUpdateBox = styled.div`
  display: flex;
  align-items: center;
  gap: 0 15px;
`;

const StyledItemEditButton = styled.button`
  border: none;
  background: none;
  width: 35px;
  font-size: 1.4rem;
  font-weight: 500;
  color: #007aff;
  cursor: pointer;
`;

const StyledItemRemoveCheckBox = styled.input`
  display: none;

  &:checked + label svg {
    /* fill: #4caf50; */
    fill: #61d377;
  }
`;

const StyledItemRemoveCheckBoxLabel = styled.label`
  pointer-events: auto;
`;

const StyledItemRemoveCheckBoxSvg = styled.svg`
  display: flex;
  cursor: pointer;
  align-items: center;
`;

const AdminEditItem = ({item, checked, onChange}) => {

  const navigate = useNavigate();

  const handleEditClick = () => {
    
    navigate(`${item.id}`);

  };

  return (
    <StyledItem>
      <StyledItemInfo to={`/places/${item.category}/${encodeURIComponent(item.name)}/${item.id}`}>
        <StyledItemThumbnailBox>
          <StyledItemThumbnail src={item.thumbnailUrl} alt="이미지" />
        </StyledItemThumbnailBox>
        <StyledItemTextBox>
          <StyledPlaceText>{item.name}</StyledPlaceText>
          <StyledAddressText>{item.address}</StyledAddressText>
        </StyledItemTextBox>
      </StyledItemInfo>
      <StyledItemUpdateBox>
        <StyledItemEditButton onClick={() => handleEditClick()}>수정</StyledItemEditButton>
        <StyledItemRemoveCheckBox
          type="checkbox"
          id={`checkbox-${item.id}`}
          checked={checked}
          onChange={onChange}
        />
        <StyledItemRemoveCheckBoxLabel htmlFor={`checkbox-${item.id}`}>
          <StyledItemRemoveCheckBoxSvg
            width="24"
            height="24"
            viewBox="0 0 20 20"
            fill="#DDE2E3"
            xmlns="http://www.w3.org/2000/svg"
          >
            <rect
              x="0.5"
              y="0.5"
              width="19"
              height="19"
              rx="9.5"
              fill="current"
            ></rect>
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M14.6549 6.27069C15.0723 6.6324 15.1174 7.26396 14.7557 7.68131L9.63127 13.5941C9.19265 14.1002 8.40738 14.1002 7.96876 13.5941L5.24433 10.4505C4.88262 10.0332 4.92773 9.40163 5.34509 9.03992C5.76244 8.67822 6.394 8.72333 6.7557 9.14068L8.80002 11.4995L13.2443 6.37145C13.606 5.9541 14.2376 5.90899 14.6549 6.27069Z"
              fill="white"
            ></path>
          </StyledItemRemoveCheckBoxSvg>
        </StyledItemRemoveCheckBoxLabel>
      </StyledItemUpdateBox>
    </StyledItem>
  );
};

export default AdminEditItem;
