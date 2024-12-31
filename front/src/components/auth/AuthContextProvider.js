import React, { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getLogout, postLogin } from "../../apis/auth";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLogin, setIsLogin] = useState(null);
  // const [isLogin, setIsLogin] = useState(false);
  const [memberInfo, setMemberInfo] = useState({});
  const [loading, setLoading] = useState(true); // 이전코드
  // const { setLoadingState } = useLoading(); // 수정해야할 코드
  const navigate = useNavigate();

  const loginSetting = () => {
    console.log("loginSetting start");
    const token = localStorage.getItem("access_token");
    const storedMemberInfo = localStorage.getItem("member_info");

    if (token && storedMemberInfo) {
      const parseMemberInfo = JSON.parse(storedMemberInfo);
      setIsLogin(true);
      setMemberInfo(parseMemberInfo);
    } else {
      setIsLogin(false);
      setMemberInfo({});
      localStorage.removeItem("member_info"); // 추가코드
    }

    setLoading(false); // 이전코드
    console.log("loginSetting end");
  };

  const login = async (username, password) => {
    localStorage.removeItem("access_token");
    try {
      const response = await postLogin({
        username: username,
        password: password,
      });

      console.log("response = ", response);
      const authorizationHeader = response.headers["authorization"];
      const loginResponse = response.data.data;

      if (authorizationHeader) {
        const accessToken = authorizationHeader.replace("Bearer ", "");
        const memberInfo = {
          username: loginResponse.username,
          role: loginResponse.role,
        };
        localStorage.setItem("access_token", accessToken);
        localStorage.setItem("member_info", JSON.stringify(memberInfo));
      }

      if (response.status === 200) {
        loginSetting();
        navigate("/");
      }
    } catch (error) {
      console.error("로그인 오류:", error);
    } 
  };

  const logout = async () => {
    setIsLogin(false);
    setMemberInfo({});
    localStorage.removeItem("access_token");
    localStorage.removeItem("member_info");

    try {
      const response = await getLogout();
      console.log("response = ", response);
      console.log("logout success");
      navigate("/");
    } catch (error) {
      console.error("logout error = ", error);
    } 
  };

  useEffect(() => {
    loginSetting();
  }, []);

  return (
    <AuthContext.Provider
      value={{ isLogin, memberInfo, login, logout, loading }} // loading 제거
    >
      {children}
    </AuthContext.Provider>
  );
};
