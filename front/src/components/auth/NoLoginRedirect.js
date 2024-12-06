import { useContext } from "react";
import { AuthContext } from "./AuthContextProvider";
import { Navigate, Outlet } from "react-router-dom";

const NoLoginRedirect = () => {
  const { isLogin } = useContext(AuthContext);
  console.log("NoLoginRedirect");
  console.log("isLogin = ", isLogin);

  if (isLogin === false) {
    return <Navigate to="/" />;
  }

  return <Outlet />;
};

export default NoLoginRedirect;
