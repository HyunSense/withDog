import { useRef, useState } from "react";

const EditImageUpload = ({images, onChange }) => {
  // const [newImages, setNewImages] = useState();
  const [removedImages, setRemovedImages] = useState([]);
  const [selectedIndex, setSelectedIndex] = useState(null);
  const fileRef = useRef();

  const handleAddClick = () => {
    fileRef.current.click();
  };

  const handleAddImage = (e) => {
    const image = e.target.files[0];

    if (images.length >= 5) {
      alert("이미지는 최대 5개 입니다.");
      return;
    }

    if (image) {
    images((prev) => [...prev, image]);
    }
  };
  // newImages가 필요한가?
  const handleRemoveImage = () => {
    if (selectedIndex !== null) {
      // const updateImages = images.filter((_, index) => index !== selectedIndex);
      setRemovedImages((prev) => [...prev, images[selectedIndex]]);
      setSelectedIndex(null);
    }
  };

  const handleSelectImage = (index) => {
    setSelectedIndex(index);
  };

  const handleMoveUp = () => {
    if (selectedIndex !== null && selectedIndex > 0) {
      // 첫 번째가 아닌 경우에만
      const moveImages = [...images];
      [moveImages[selectedIndex - 1], moveImages[selectedIndex]] = [
        moveImages[selectedIndex],
        moveImages[selectedIndex - 1],
      ];
      //   setImages(newImages);
      setSelectedIndex(selectedIndex - 1);
    }
  }
};

export default EditImageUpload;
