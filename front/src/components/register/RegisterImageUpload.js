import { useRef, useState } from "react";
import addIcon from "../../assets/images/add.png";
import removeIcon from "../../assets/images/remove.png";
import upDownIcon from "../../assets/images/up-down.png";
import * as S from "../../styles/RegisterImageUpload.Styled";

const RegisterImageUpload = ({ images, onChange }) => {
  //   const [images, setImages] = useState([]);
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
      //   setImages([...images, image]);
      onChange([...images, image]);
    }
  };

  const handleRemoveImage = () => {
    if (selectedIndex !== null) {
      const updateImages = images.filter((_, index) => index !== selectedIndex);
      //   setImages(updateImages); // 이부분만 전체로 덮어씌운다?
      onChange(updateImages);
      setSelectedIndex(null);
    }
  };

  const handleSelectImage = (index) => {
    setSelectedIndex(index);
  };

  const handleMoveUp = () => {
    if (selectedIndex !== null && selectedIndex > 0) {
      // 첫 번째가 아닌 경우에만
      const newImages = [...images];
      [newImages[selectedIndex - 1], newImages[selectedIndex]] = [
        newImages[selectedIndex],
        newImages[selectedIndex - 1],
      ];
      //   setImages(newImages);
      onChange(newImages);
      setSelectedIndex(selectedIndex - 1);
    }
  };

  const handleMoveDown = () => {
    if (selectedIndex !== null && selectedIndex < images.length - 1) {
      const newImages = [...images];
      [newImages[selectedIndex + 1], newImages[selectedIndex]] = [
        newImages[selectedIndex],
        newImages[selectedIndex + 1],
      ];
      //   setImages(newImages);
      onChange(newImages);
      setSelectedIndex(selectedIndex + 1);
    }
  };

  return (
    <S.StyledRegisterImageUpload>
      <S.StyledImageBox>
        {images.map((image, index) => (
          <S.StyledImage
            key={index}
            selected={index === selectedIndex}
            onClick={() => handleSelectImage(index)}
          >
            {image.name}
          </S.StyledImage>
        ))}
      </S.StyledImageBox>
      <S.StyledImageControlBox>
        <S.StyledFileInput
          type="file"
          accept=".jpg, .png"
          ref={fileRef}
          onChange={handleAddImage}
        />
        <S.StyledImageControlIcon
          src={upDownIcon}
          rotate="180deg"
          onClick={handleMoveUp}
        />
        <S.StyledImageControlIcon src={upDownIcon} onClick={handleMoveDown} />
        <S.StyledImageControlIcon
          src={addIcon}
          rotate="180deg"
          onClick={handleAddClick}
        />
        <S.StyledImageControlIcon
          src={removeIcon}
          onClick={handleRemoveImage}
        />
      </S.StyledImageControlBox>
    </S.StyledRegisterImageUpload>
  );
};

export default RegisterImageUpload;
