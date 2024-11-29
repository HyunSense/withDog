import { useContext } from "react";
import { AuthContext } from "./AuthContextProvider";
import { Navigate, Outlet } from "react-router-dom";
import Loading from "../common/Loading";

const PrivateRoute = ({ roles }) => {
  const { isLogin, memberInfo, loading } = useContext(AuthContext);

  if (loading) {
    return <Loading />;
  }

  if (!isLogin) {
    console.log("isLogin = ", isLogin);
    console.log("is Not Login");
    return <Navigate to="/login" />; // 랜더링 중 즉시 리다이렉션
  }
  
  if (!roles.includes(memberInfo.role)) {
    console.log("No Authority = ", memberInfo.role);
    return <Navigate to="/" />;
  }

  return <Outlet />; //?
};

export default PrivateRoute;
