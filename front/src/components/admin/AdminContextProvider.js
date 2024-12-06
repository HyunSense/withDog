import { createContext, useEffect, useState } from "react";

export const AdminContext = createContext();

export const AdminProvider = ({ children }) => {
    // place를 담을지 place.name만 담을지
  const [place, setPlace] = useState(null);

  useEffect(() => {
    console.log("Admin Proivder");
  })
  
  return (
    <AdminContext.Provider value={{ place, setPlace }}>
      {children}
    </AdminContext.Provider>
  );
};
