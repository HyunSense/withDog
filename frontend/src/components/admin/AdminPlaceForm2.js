import { useState } from "react";
import * as S from "../../styles/AdminSave.Styled";
import SaveImageUpload from "./SaveImageUpload";
import Postcode from "./Postcode";
import usePlaceForm from "../../hooks/usePlaceForm";
import { CATEGORY_MAP } from "../../constants/categoryMap";
import FilterContainer from "../searchFilter/FilterContainer";
import { FILTER_OPTIONS } from "../../constants/filters";

const AdminPlaceForm = ({ initValues, isEdit, onSubmit }) => {
  const [selectedFilters, setSelectedFilters] = useState(initValues.filters || {});

  const [selectedCategory, setSelectedCategory] = useState(initValues.category || "camp");
  const {
    values,
    images,
    handleTextInputChange,
    handleAddressChange,
    handleBlogUrlChange,
    handleImageChange,
    removedImages,
  } = usePlaceForm(initValues);

  const validateRequiredFilters = (filters) => {
    const requiredFilters = FILTER_OPTIONS.filter(f => f.isRequired);
    const missingFilters = requiredFilters.filter(f => !filters[f.id] || filters[f.id].length === 0);

    if (missingFilters.length > 0) {
      const missingTitle = missingFilters.map(f => f.title).join(", ");
      alert(`다음 필터는 필수입니다.\n${missingTitle}`);
      return false;
    }

    return true;
  }

  //backend 서버단 image transaction 처리 생각
  // 별도의 ResponseDto 필요? blog, image
  const handleOnSubmit = (e) => {
    e.preventDefault();

    if (!validateRequiredFilters(selectedFilters)) {
      return; // 필터 유효성 검사
    }

    const categoryId = CATEGORY_MAP[selectedCategory];
    const formData = new FormData();
    formData.append("filters", JSON.stringify(selectedFilters));
    formData.append("categoryId", categoryId);
    formData.append("name", values.name);
    formData.append("phone", values.phone);
    formData.append("addressPart1", values.addressPart1);
    formData.append("addressPart2", values.addressPart2);
    formData.append("price", values.price);
    formData.append("reservationUrl", values.reservationUrl);
    const filteredblogUrls = values.blogUrls.filter((url) => url !== "");
    formData.append("blogUrls", filteredblogUrls);

    if (isEdit === true) {
      // filter는 조건에 충족하는것만 거른다.
      const imagePositions = images.map((image, index) => ({
        id: image.id,
        position: index,
      }));
      const filteredImagePositions = imagePositions.filter((image) => image.id);

      filteredImagePositions.forEach((image, index) => {
        formData.append(`updateImages[${index}].imageId`, image.id);
        formData.append(`updateImages[${index}].imagePosition`, image.position);
      })

      const filteredRemovedImages = removedImages.filter((image) => image.id);
      filteredRemovedImages.forEach((image, index) =>
        formData.append(`updateImages.removedId[${index}]`, image.id)
      );

      filteredRemovedImages.forEach((image, index) => {
        formData.append(`removedImageIds[${index}]`, image.id);
      })

      const newImages = images.reduce((acc, image, index) => {
        if (!("id" in image)) {
          acc.push({name: image.name ,image: image, position: index });
        }
        return acc;
      }, []);
      newImages.forEach((image, index) => {
        formData.append(`newImages[${index}].image`, image.image);
        formData.append(`newImages[${index}].position`, image.position);
        formData.append(`newImages[${index}].name`, image.name);
      });

    } else {
      images.forEach((image, index) => {
        formData.append(`images[${index}].image`, image);
        formData.append(`images[${index}].position`, index);
        formData.append(`images[${index}].name`, image.name);
      });
    }
    console.log("selectedFilters = ", selectedFilters)
    onSubmit(formData);
  };

  return (
    <S.StyledAdminSave>
      <S.StyledSaveForm onSubmit={handleOnSubmit}>
        <FilterContainer mode="admin" selectedFilters={selectedFilters} setSelectedFilters={setSelectedFilters}/>
        <S.StyledImageUploadBox>
          <S.StyledText>이미지등록(최대 5개)</S.StyledText>
          <SaveImageUpload images={images} onChange={handleImageChange} />
        </S.StyledImageUploadBox>
        <S.StyledInputBox>
          <S.StyledText>이름</S.StyledText>
          <S.StyledInput
            type="text"
            name="name"
            placeholder="ex) withDog 캠핑장"
            onChange={handleTextInputChange}
            value={values.name}
          />
        </S.StyledInputBox>
        <S.StyledInputBox>
          <S.StyledText>전화번호</S.StyledText>
          <S.StyledInput
            type="text"
            name="phone"
            placeholder="ex) 010-1234-5678"
            onChange={handleTextInputChange}
            value={values.phone}
          />
        </S.StyledInputBox>
        <S.StyledInputBox>
          <S.StyledText>주소</S.StyledText>
          <S.StyledAddressInputBox>
            <S.StyledInput
              type="text"
              width="100%"
              value={`${values.addressPart1} ${values.addressPart2}`.trim()}
              onChange={handleTextInputChange}
              readOnly
            />
            <Postcode addressChange={handleAddressChange}>검색</Postcode>
          </S.StyledAddressInputBox>
        </S.StyledInputBox>
        <S.StyledInputBox>
          <S.StyledText>가격</S.StyledText>
          <S.StyledInput
            type="number"
            name="price"
            placeholder="ex) 50000"
            onChange={handleTextInputChange}
            value={values.price}
          />
        </S.StyledInputBox>
        <S.StyledInputBox>
          <S.StyledText>예약 링크</S.StyledText>
          <S.StyledInput
            type="text"
            name="reservationUrl"
            placeholder="ex) https://withDog.reservation.com"
            onChange={handleTextInputChange}
            value={values.reservationUrl}
          />
        </S.StyledInputBox>
        <S.StyledInputBox direction="column" $alignItems="flex-start">
          <S.StyledText>
            블로그 링크 *http://, https:// 를 반드시 포함해주세요.*
          </S.StyledText>
          {values.blogUrls.map((url, index) => (
            <S.StyledInput
              width="100%"
              type="text"
              key={index}
              value={url}
              onChange={(e) => handleBlogUrlChange(e, index)}
            />
          ))}
        </S.StyledInputBox>
        <S.StyeldRegisterButton type="submit" $marginTop="50px">
          {isEdit ? "수정하기" : "등록하기"}
        </S.StyeldRegisterButton>
      </S.StyledSaveForm>
    </S.StyledAdminSave>
  );
};

export default AdminPlaceForm;
