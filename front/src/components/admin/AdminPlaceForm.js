import { useState } from "react";
import * as S from "../../styles/AdminSave.Styled";
import SaveImageUpload from "./SaveImageUpload";
import SaveImageUpload2 from "./SaveImageUpload2";
import Postcode from "./Postcode";
import { blogsValidation } from "../validation/PlaceFormValidation.";

const AdminPlaceForm = ({ initValues, isEdit, onSubmit }) => {
  const [selected, setSelected] = useState(initValues.category || "camp");
  const [values, setValues] = useState({
    name: initValues.name || "",
    phone: initValues.phone || "",
    addressPart1: initValues.addressPart1 || "",
    addressPart2: initValues.addressPart2 || "",
    price: initValues.price || "",
    reservationUrl: initValues.reservationUrl || "",
    blogUrls: initValues.placeBlogs.map((blog) => blog.blogUrl) || ["", "", "", ""],
  });

  const [images, setImages] = useState(initValues.placeImages || []);
  const [removedImages, setRemovedImages] = useState([]); // 추가코드

  const handleTextInputChange = (e) => {
    const { name, value } = e.target;

    setValues((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddressChange = (address) => {
    setValues((prev) => ({
      ...prev,
      addressPart1: address.addressPart1,
      addressPart2: address.addressPart2,
    }));
  };

  const handleBlogUrlChange = (e, index) => {
    const { value } = e.target;
    const newBlogUrls = [...values.blogUrls];
    newBlogUrls[index] = value;

    setValues((prev) => ({ ...prev, blogUrls: newBlogUrls }));
  };

  const handleImageChange = (newImages, removed) => {
    setImages(newImages);
    // setRemovedImages((prev) => [...prev, removedImages]);
    if (removed.length > 0) {
        setRemovedImages((prev) => [...prev, removed]);
    }
  };

  //backend 서버단 image transaction 처리 생각
  // 별도의 ResponseDto 필요? blog, image
  const handleOnSubmit = (e) => {
    e.preventDefault();
    console.log("Images = ", images);
    console.log("removedImages = ", removedImages);

    // const textFormData = {
    //   ...values,
    //   category: selected,
    //   blogUrls: values.blogUrls.filter((url) => url !== ""),
    // };

    // const formData = new FormData();
    // images.forEach((image) => formData.append("images", image));

    // const jsonValues = JSON.stringify(textFormData);
    // const blob = new Blob([jsonValues], { type: "application/json" });
    // formData.append("placeData", blob);

    // if(blogsValidation(textFormData.blogUrls)){
    //     onSubmit(formData);

    // } else {
    //   console.log("blogsValidation Failed");
    // };

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
