import React, { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {getLogout, getRefreshToken, postLogin} from "../../apis/auth";
import {setAccessToken} from "../../apis/api";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLogin, setIsLogin] = useState(null);
  const [memberInfo, setMemberInfo] = useState({});
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {

    const checkAuthStatus = async () => {
      const storedMemberInfo = localStorage.getItem("member_info");
      if (storedMemberInfo) {
        setMemberInfo(JSON.parse(storedMemberInfo));
        setIsLogin(true);
      }

      try {
        const refreshResponse = await getRefreshToken();
        const newAccessToken = refreshResponse.headers["authorization"]?.replace("Bearer ", "");
        const latestMemberInfo = refreshResponse.data.data;

        if (!newAccessToken || !latestMemberInfo) {
          throw new Error("Invalid refresh response");
        }

        setAccessToken(newAccessToken);
        setIsLogin(true);
        setMemberInfo(latestMemberInfo);
        localStorage.setItem("member_info", JSON.stringify(latestMemberInfo));

      } catch (error) {
        setIsLogin(false);
        setMemberInfo({});
        setAccessToken(null);
        localStorage.removeItem("member_info");
      } finally {
        setLoading(false);
      }
    };

    checkAuthStatus();

  }, []);
  
  const login = async (username, password) => {
    // localStorage.removeItem("access_token");
    try {
      const response = await postLogin({
        username: username,
        password: password,
      });

      const authorizationHeader = response.headers["authorization"];
      const loginResponse = response.data.data;

      if (authorizationHeader && loginResponse) {
        const memberInfo = {
          username: loginResponse.username,
          role: loginResponse.role,
        };

        localStorage.setItem("member_info", JSON.stringify(memberInfo));
        setIsLogin(true);
        setMemberInfo(memberInfo);

        const accessToken = authorizationHeader.replace("Bearer ", "");
        setAccessToken(accessToken);

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
    localStorage.removeItem("member_info");
    setAccessToken(null);

    try {
      await getLogout();
      alert("로그아웃 되었습니다.");
      navigate("/");
    } catch (error) {
      console.error("logout error = ", error);
    } 
  };


  return (
    <AuthContext.Provider
      value={{ isLogin, memberInfo, login, logout, loading, setIsLogin, setMemberInfo }}
    >
      {children}
    </AuthContext.Provider>
  );
};
