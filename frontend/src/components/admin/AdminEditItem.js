import React from "react";

import { useNavigate } from "react-router-dom";
import * as S from "../../styles/AdminEditItem.Styled";

const AdminEditItem = ({ item, checked, onChange }) => {
  const navigate = useNavigate();

  const handleEditClick = () => {
    navigate(`${item.id}`);
  };

  return (
    <S.StyledItem>
      <S.StyledItemInfo
        to={`/places/${item.category}/${encodeURIComponent(item.name)}/${
          item.id
        }`}
      >
        <S.StyledItemThumbnailBox>
          <S.StyledItemThumbnail src={item.thumbnailUrl} alt="이미지" />
        </S.StyledItemThumbnailBox>
        <S.StyledItemTextBox>
          <S.StyledPlaceText>{item.name}</S.StyledPlaceText>
          <S.StyledAddressText>{item.address}</S.StyledAddressText>
        </S.StyledItemTextBox>
      </S.StyledItemInfo>
      <S.StyledItemUpdateBox>
        <S.StyledItemEditButton onClick={() => handleEditClick()}>
          수정
        </S.StyledItemEditButton>
        <S.StyledItemRemoveCheckBox
          type="checkbox"
          id={`checkbox-${item.id}`}
          checked={checked}
          onChange={onChange}
        />
        <S.StyledItemRemoveCheckBoxLabel htmlFor={`checkbox-${item.id}`}>
          <S.StyledItemRemoveCheckBoxSvg
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
          </S.StyledItemRemoveCheckBoxSvg>
        </S.StyledItemRemoveCheckBoxLabel>
      </S.StyledItemUpdateBox>
    </S.StyledItem>
  );
};

export default AdminEditItem;
