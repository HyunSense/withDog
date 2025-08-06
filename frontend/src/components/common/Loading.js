import React from "react";
import { MoonLoader } from "react-spinners";

const Loading = () => {
  return (
    <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 min-w-[720px] min-h-screen z-50 flex flex-col justify-center items-center bg-sky-50">
      <MoonLoader color="#1491BE" speedMultiplier="0.8" />
      <p className="text-sm font-medium mt-2">잠시만 기다려주세요.</p>
    </div>
  );
};

export default Loading;
