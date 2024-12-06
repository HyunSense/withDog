import { useState } from "react";
import * as S from "../../styles/AdminSave.Styled";
import SaveImageUpload from "./SaveImageUpload";
import SaveImageUpload2 from "./SaveImageUpload2";
import Postcode from "./Postcode";
import { blogsValidation } from "../validation/PlaceFormValidation.";
import usePlaceForm from "../../hooks/usePlaceForm";
import { useNavigate } from "react-router-dom";

const AdminPlaceForm = ({ initValues, isEdit, onSubmit }) => {
  const [selected, setSelected] = useState(initValues.category || "camp");
  const {
    values,
    images,
    setImages,
    handleTextInputChange,
    handleAddressChange,
    handleBlogUrlChange,
    handleImageChange,
    removedImages,
  } = usePlaceForm(initValues);

  //backend 서버단 image transaction 처리 생각
  // 별도의 ResponseDto 필요? blog, image
  const handleOnSubmit = (e) => {
    e.preventDefault();
    console.log("images = ", images);
    console.log("removedImages = ", removedImages);
    // 포지션을 먼저 보낸다.
    // id가 포함되어있다면 포지션만 바꿔준다.
    // 새 이미지만 분류하여 새 이미지를 등록한다.
    // 이때 포지션에서 아이디가 없는 포지션만 새이미지의 포지션으로 등록해준다.
    // removeImages에서도 id가 포함되어있지 않다면 removeImages는 [] 또는 null로 보낸다.
    // removeImages에서 id가 포함되어있지 않다는것은 새롭게 추가한이미지를 다시 삭제했다는 뜻이기 때문이다.

    const formData = new FormData();
    formData.append("category", selected);
    formData.append("name", values.name);
    formData.append("phone", values.phone);
    formData.append("addressPart1", values.addressPart1);
    formData.append("addressPart2", values.addressPart2);
    formData.append("price", values.price);
    formData.append("reservationUrl", values.reservationUrl);
    formData.append("blogUrls", values.blogUrls.filter((url) => url !== ""));
    if (isEdit === true) {
      // filter는 조건에 충족하는것만 거른다.
      const imagePositions = images.map((image, index) => ({id: image.id, position: index}));
      console.log("imagePositions", imagePositions);
      
      const updateRemovedImages = removedImages.filter((image) => (image.id));
      console.log("updateRemovedImages = ", updateRemovedImages);
      
      const newImages = images.filter((image) =>!("id" in image));
      console.log("newImages = ", newImages);
      // setImages((prev) => [...prev]); //필요? 그냥 새로고침?
      // submit할때 검증에서 실패하면 작성한 내용들이 그대로 다시 담겨있어야됨
      console.log("===========================");
      // const updateImages = {imagePositions: imagePositions, removedId: updateRemovedImages};
      // formData.append("updateImages", updateImages);
      // formData.append("newImages", newImages);
      imagePositions.forEach((image, index) => { 
        formData.append(`updateImages.images[${index}].id`, image.id);
        formData.append(`updateImages.images[${index}].position`, image.position);
        // formData.append(`updateImages.images.id`, image.id);
        // formData.append(`updateImages.images.position`, image.position);
      });
      // updateRemovedImages.forEach((image, index) => formData.append("updateImages.removedId", image.id));
      updateRemovedImages.forEach((image, index) => formData.append(`updateImages.removedId[${index}]`, image.id));
      newImages.forEach((image) => formData.append("newImages", image));
    } else {
      images.forEach((image) => formData.append("images", image));
    }
    
    const entries = formData.entries();
    for (const pair of entries) {
        console.log(pair[0]+ ': ' + pair[1]); 
    }
    onSubmit(formData);
    //=====================

    // const textFormData = {
    //   ...values,
    //   category: selected,
    //   blogUrls: values.blogUrls.filter((url) => url !== ""),
    // };
    
    // if (isEdit === true) {
    //   textFormData.removedImages = removedImages;
    // }
   
    // const formData = new FormData();
    // images.forEach((image) => formData.append("images", image));

    // const jsonValues = JSON.stringify(textFormData);
    // const blob = new Blob([jsonValues], { type: "application/json" });
    // formData.append("placeData", blob);

    // if (blogsValidation(textFormData.blogUrls)) {
    //   onSubmit(formData);
    // } else {
    //   console.log("blogsValidation Failed");
    // }

    //=====================
  };

  return (
    <S.StyledAdminSave>
      <S.StyledSaveForm onSubmit={handleOnSubmit}>
        <S.StyledCategoryBox>
          <S.StyledText>종류</S.StyledText>
          <S.StyledCategoryButtonBox>
            <S.StyledCategoryButton
              type="button"
              $borderRadius="5px 0px 0px 5px"
              $isSelected={selected === "camp"}
              onClick={() => setSelected("camp")}
            >
              <S.StyledText>캠핑</S.StyledText>
            </S.StyledCategoryButton>
            <S.StyledCategoryButton
              type="button"
              $borderRadius="0px 5px 5px 0px"
              $borderLeft="none"
              $isSelected={selected === "park"}
              onClick={() => setSelected("park")}
            >
              <S.StyledText>공원</S.StyledText>
            </S.StyledCategoryButton>
          </S.StyledCategoryButtonBox>
        </S.StyledCategoryBox>
        <S.StyledImageUploadBox>
          <S.StyledText>이미지등록(최대 5개)</S.StyledText>
          {/* <SaveImageUpload images={images} onChange={handleImageChange} /> */}
          <SaveImageUpload2 images={images} onChange={handleImageChange} />
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
