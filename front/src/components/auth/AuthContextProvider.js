import React, { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getLogout, postLogin } from "../../apis/auth";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLogin, setIsLogin] = useState(null);
  const [memberInfo, setMemberInfo] = useState({});
  const [loading, setLoading] = useState(true);
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
      localStorage.removeItem("member_info");
    }

    setLoading(false);
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
      if (error.response.data.code === 'LF') {
        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
      }
    } 
  };

  const logout = async () => {
    setIsLogin(false);
    setMemberInfo({});
    localStorage.removeItem("access_token");
    localStorage.removeItem("member_info");

    try {
      await getLogout();
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
      value={{ isLogin, memberInfo, login, logout, loading }}
    >
      {children}
    </AuthContext.Provider>
  );
};
