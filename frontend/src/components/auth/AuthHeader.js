import React from "react";
import PrevButton from "../common/PrevButton";
import { Link } from "react-router-dom";

const AuthHeader = ({ title, buttonText, link }) => {
  return (
    <header className="flex justify-between items-center px-4 py-5 mb-10">
      <div className="w-16 flex justify-start">
        <PrevButton to="/" />
      </div>
      <p className="text-xl font-semibold text-neutral-800 text-center flex-1">
        {title}
      </p>
      <div className="w-16 flex justify-end">
        <Link
          to={link}
          className="flex justify-center items-center border border-solid border-gray-300 rounded-md cursor-pointer bg-white px-2 py-1"
        >
          <span className="text-xs font-medium text-neutral-800">
            {buttonText}
          </span>
        </Link>
      </div>
    </header>
  );
};

export default AuthHeader;
