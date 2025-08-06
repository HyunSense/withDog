import React from "react";

const Container = ({ children }) => {
  return <div className="flex flex-col w-full min-h-[calc(var(--vh,1vh)_*_100)] mx-auto md:w-[720px] bg-white">{children}</div>;
};

export default Container;
