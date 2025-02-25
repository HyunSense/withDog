import { useState } from "react";

const usePlaceForm = (initValues) => {

  const blogUrls = initValues.placeBlogs ? initValues.placeBlogs.map((blog) => blog.blogUrl) : [];
  while (blogUrls.length < 4) {
    blogUrls.push("");
  }

  const [values, setValues] = useState({
    name: initValues.name || "",
    phone: initValues.phone || "",
    addressPart1: initValues.addressPart1 || "",
    addressPart2: initValues.addressPart2 || "",
    price: initValues.price === undefined || initValues.price === null ? "" : initValues.price,
    reservationUrl: initValues.reservationUrl || "",
    blogUrls: blogUrls,
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
