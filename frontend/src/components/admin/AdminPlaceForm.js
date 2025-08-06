import { useState } from "react";
import SaveImageUpload from "./SaveImageUpload";
import Postcode from "./Postcode";
import usePlaceForm from "../../hooks/usePlaceForm";
import FilterContainer from "../searchFilter/FilterContainer";
import { FILTER_OPTIONS } from "../../constants/filters";

const AdminPlaceForm = ({ initValues, isEdit, onSubmit }) => {
  const [selectedFilters, setSelectedFilters] = useState(
    initValues.filters || {}
  );

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
    const requiredFilters = FILTER_OPTIONS.filter((f) => f.isRequired);
    const missingFilters = requiredFilters.filter(
      (f) => !filters[f.id] || filters[f.id].length === 0
    );

    if (missingFilters.length > 0) {
      const missingTitle = missingFilters.map((f) => f.title).join(", ");
      alert(`다음 필터는 필수입니다.\n${missingTitle}`);
      return false;
    }

    return true;
  };

  //backend 서버단 image transaction 처리 필요
  const handleOnSubmit = (e) => {
    e.preventDefault();

    if (!validateRequiredFilters(selectedFilters)) {
      return; // 필터 유효성 검사
    }

    const formData = new FormData();
    for (const key in selectedFilters) {
      selectedFilters[key].forEach((value) => {
        formData.append(`filters[${key}]`, value);
      });
    }
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
      });

      const filteredRemovedImages = removedImages.filter((image) => image.id);
      filteredRemovedImages.forEach((image, index) => {
        formData.append(`removedImageIds[${index}]`, image.id);
      });

      const newImages = images.reduce((acc, image, index) => {
        if (!("id" in image)) {
          acc.push({ name: image.name, image: image, position: index });
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

    onSubmit(formData);
  };

  return (
    <main className="flex justify-center px-4 pb-20 bg-white">
      <form className="flex flex-col w-full gap-y-2" onSubmit={handleOnSubmit}>
        <FilterContainer
          mode="admin"
          selectedFilters={selectedFilters}
          setSelectedFilters={setSelectedFilters}
        />
        <div>
          <p className="text-sm">이미지등록(최대 5개)</p>
          <SaveImageUpload images={images} onChange={handleImageChange} />
        </div>
        <div className="flex justify-between items-center">
          <span>이름</span>
          <input
            className="w-4/5 h-6 border rounded-md text-sm px-2 custom-input"
            type="text"
            name="name"
            placeholder="ex) withDog 캠핑장"
            onChange={handleTextInputChange}
            value={values.name}
          />
        </div>
        <div className="flex justify-between items-center">
          <span>전화번호</span>
          <input
            className="w-4/5 h-6 border rounded-md text-sm px-2 custom-input"
            type="text"
            name="phone"
            placeholder="ex) 010-1234-5678"
            onChange={handleTextInputChange}
            value={values.phone}
          />
        </div>
        <div className="flex justify-between items-center">
          <span>주소</span>
          <div className="flex justify-end items-center w-4/5 gap-1">
            <input
              className="w-full h-6 border rounded-md text-sm px-2 custom-input"
              type="text"
              value={`${values.addressPart1} ${values.addressPart2}`.trim()}
              onChange={handleTextInputChange}
              readOnly
            />
            <Postcode addressChange={handleAddressChange}>검색</Postcode>
          </div>
        </div>
        {/* <div className="flex justify-between items-center">
          <span>가격</span>
          <input
            className="w-4/5 h-6 border border-gray-300 rounded-md text-sm px-2 custom-input"
            type="number"
            name="price"
            placeholder="ex) 50000"
            onChange={handleTextInputChange}
            value={values.price}
          />
        </div> */}
        <div className="flex justify-between items-center">
          <span>예약 링크</span>
          <input
            className="w-4/5 h-6 border rounded-md text-sm px-2 custom-input"
            type="text"
            name="reservationUrl"
            placeholder="ex) https://withDog.reservation.com"
            onChange={handleTextInputChange}
            value={values.reservationUrl}
          />
        </div>
        <div className="flex flex-col justify-between items-start gap-2">
          <span>블로그 링크 *http://, https:// 를 반드시 포함해주세요.*</span>
          {values.blogUrls.map((url, index) => (
            <input
              className="w-full h-6 border rounded-md text-sm px-2 custom-input"
              type="text"
              key={index}
              value={url}
              onChange={(e) => handleBlogUrlChange(e, index)}
            />
          ))}
        </div>
        <button
          className="flex justify-center items-center border rounded-md  bg-white h-10 text-xl font-bold mt-10"
          type="submit"
        >
          {isEdit ? "수정하기" : "등록하기"}
        </button>
      </form>
    </main>
  );
};

export default AdminPlaceForm;
