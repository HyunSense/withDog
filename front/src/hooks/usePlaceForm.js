import { useState } from "react";

const usePlaceForm = (initValues) => {
  const [selected, setSelected] = useState(initValues.category || "camp");
  const [values, setValues] = useState({
    name: initValues.name || "",
    phone: initValues.phone || "",
    addressPart1: initValues.addressPart1 || "",
    addressPart2: initValues.addressPart2 || "",
    price: initValues.price || "",
    reservationUrl: initValues.reservationUrl || "",
    blogUrls: initValues.placeBlogs
      ? initValues.placeBlogs.map((blog) => blog.blogUrl)
      : ["", "", "", ""], // placeBlogs 반복 수정필요 블로그 등록안할시 input창 사라짐
  });

  const [images, setImages] = useState(initValues.placeImages || []);
  const [removedImages, setRemovedImages] = useState([]);

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
    if (removed.length > 0) {
      setRemovedImages((prev) => [...prev, ...removed]);
    }
  };

  return {
    selected,
    setSelected,
    values,
    setValues,
    images,
    setImages,
    removedImages,
    setRemovedImages,
    handleTextInputChange,
    handleAddressChange,
    handleBlogUrlChange,
    handleImageChange,
  };
};

export default usePlaceForm;
