import { useRef, useState } from "react";
import addIcon from "../../assets/images/add-98px.png";
import removeIcon from "../../assets/images/remove-98px.png";
import upDownIcon from "../../assets/images/up-down-98px.png";

const RegisterImageUpload = ({ images, onChange }) => {
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
      onChange([...images, image], []);
    }
  };

  const handleRemoveImage = () => {
    if (selectedIndex !== null) {
      const updateImages = images.filter((_, index) => index !== selectedIndex);
      const removedId = images[selectedIndex].id;
      onChange(updateImages, [{ id: removedId }]);
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

      onChange(newImages, []);
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

      onChange(newImages, []);
      setSelectedIndex(selectedIndex + 1);
    }
  };

  return (
    <div className="mt-1 mb-5">
      <div className="flex flex-col h-32 border rounded-md resize-none py-1 px-2 overflow-y-auto">
        {images.map((image, index) => (
          <div
            className={`text-sm p-0.5 cursor-pointer ${
              index === selectedIndex ? "bg-gray-200" : ""
            }`}
            key={index}
            selected={index === selectedIndex}
            onClick={() => handleSelectImage(index)}
          >
            {image.name}
          </div>
        ))}
      </div>
      <div className="flex justify-end items-center gap-x-2 mt-1">
        <input
          className="hidden"
          type="file"
          accept=".jpg, .png, .jpeg"
          ref={fileRef}
          onChange={handleAddImage}
        />
        <img
          className="w-6 h-6 cursor-pointer rotate-180"
          alt="Move Up"
          src={upDownIcon}
          onClick={handleMoveUp}
        />
        <img
          className="w-6 h-6 cursor-pointer"
          alt="Move Down"
          src={upDownIcon}
          onClick={handleMoveDown}
        />
        <img
          className="w-6 h-6 cursor-pointer"
          alt="Add"
          src={addIcon}
          onClick={handleAddClick}
        />
        <img
          className="w-6 h-6 cursor-pointer"
          alt="Remove"
          src={removeIcon}
          onClick={handleRemoveImage}
        />
      </div>
    </div>
  );
};

export default RegisterImageUpload;
