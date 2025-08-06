import React from "react";
import { Link } from "react-router-dom";

const PlaceItem = ({ item, to, perRow = 2 }) => {

  const widthClass =
    perRow === 3
    ? "w-[calc(33%-1px)]"
    : "w-[calc(50%-5px)]";

  return (
    <Link to={to} className={`flex flex-col ${widthClass} cursor-pointer`}>
      <div className="relative w-full pt-[100%] overflow-hidden">
        <img
          className="absolute top-0 left-0 w-full h-full object-cover rounded-lg"
          src={item.thumbnailUrl}
          alt="장소 썸네일"
        />
      </div>
      <span className="text-sm font-medium text-black mt-2">{item.name}</span>
      <span className="text-sm font-medium text-gray-500">{item.address}</span>
    </Link>
  );
}

export default PlaceItem;
