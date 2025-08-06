import React from "react";
import { useNavigate } from "react-router-dom";
import prevIcon from "../../assets/images/prev-icon.png";

const PrevButton = ({ to }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    if (to) {
      navigate(to);
    } else {
      navigate(-1);
    }
  };

  return (
    <button
      className="flex justify-center items-center w-6 h-auto border-none outline-none background-none cursor-pointer"
      onClick={handleClick}
    >
      <img className="w-full h-full" src={prevIcon} alt="이전 아이콘" />
    </button>
  );
};

export default PrevButton;
