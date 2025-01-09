import { useContext } from "react";
import { AuthContext } from "./AuthContextProvider";
import { Navigate, Outlet } from "react-router-dom";

const LoginRedirect = () => {
  const { isLogin } = useContext(AuthContext);
  console.log("LoginRedirect");
  console.log("isLogin = ", isLogin);
  if (isLogin === true) {
    return <Navigate to="/" />;
  }

  // return children;
  return <Outlet />;
};

export default LoginRedirect;
