import React from "react";

import { Link, useNavigate } from "react-router-dom";

const AdminEditItem = ({ item: place, checked, onChange }) => {
  const navigate = useNavigate();

  const handleEditClick = () => {
    navigate(`${place.id}`);
  };

  return (
    <div className="flex justify-between items-center">
      <Link
        className="flex items-center gap-x-2 w-full"
        to={`/places/${place.id}`}
      >
        <div className="w-12 h-12">
          <img
            className="w-full h-full border border-gray-300 rounded-full object-cover"
            src={place.thumbnailUrl}
            alt="이미지"
          />
        </div>
        <div className="flex flex-col justify-center gap-y-1">
          <span className="text-sm font-medium text-neutral-800">
            {place.name}
          </span>
          <span className="text-xs font-medium text-gray-500">
            {place.address}
          </span>
        </div>
      </Link>
      <div className="flex items-center gap-x-4">
        <button
          className="text-sm font-medium w-9 text-[#007bff] cursor-pointer"
          onClick={() => handleEditClick()}
        >
          수정
        </button>
        <input
          className="hidden"
          type="checkbox"
          id={`checkbox-${place.id}`}
          checked={checked}
          onChange={onChange}
        />
        <label className="cursor-pointer" htmlFor={`checkbox-${place.id}`}>
          <svg
            width="24"
            height="24"
            viewBox="0 0 20 20"
            fill="#DDE2E3"
            xmlns="http://www.w3.org/2000/svg"
          >
            <rect
              x="0.5"
              y="0.5"
              width="19"
              height="19"
              rx="9.5"
              fill="current"
            ></rect>
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M14.6549 6.27069C15.0723 6.6324 15.1174 7.26396 14.7557 7.68131L9.63127 13.5941C9.19265 14.1002 8.40738 14.1002 7.96876 13.5941L5.24433 10.4505C4.88262 10.0332 4.92773 9.40163 5.34509 9.03992C5.76244 8.67822 6.394 8.72333 6.7557 9.14068L8.80002 11.4995L13.2443 6.37145C13.606 5.9541 14.2376 5.90899 14.6549 6.27069Z"
              fill="white"
            ></path>
          </svg>
        </label>
      </div>
    </div>
  );
};

export default AdminEditItem;
