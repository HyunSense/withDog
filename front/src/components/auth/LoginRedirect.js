import { useContext } from "react";
import { AuthContext } from "./AuthContextProvider";
import { Navigate } from "react-router-dom";

const LoginRedirect = ({ children }) => {
  const { isLogin } = useContext(AuthContext);

  if (isLogin) {
    return <Navigate to="/" />;
  }

  return children;
};

export default LoginRedirect;
