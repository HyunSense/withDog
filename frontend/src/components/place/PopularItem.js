import React from "react";
import { Link } from "react-router-dom";

const PopularItem = ({ item, to }) => {
  return (
    <Link to={to} className="flex flex-col w-[calc(33%-1px)] cursor-pointer">
      <div className="relative w-full pt-[100%] overflow-hidden">
        <img
          className="absolute top-0 left-0 w-full h-full object-cover rounded-lg"
          src={item.thumbnailUrl}
          alt="인기 장소"
        />
      </div>
      <span className="text-sm font-medium text-black mt-2">{item.name}</span>
      <span className="text-sm font-medium text-gray-500">{item.address}</span>
    </Link>
  );
};

export default PopularItem;
