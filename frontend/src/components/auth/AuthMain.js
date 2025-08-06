import React from "react";

const AuthMain = ({ children }) => {
  return (
    <main className="flex flex-col justify-center items-center px-4">
      {children}
    </main>
  );
};

export default AuthMain;
