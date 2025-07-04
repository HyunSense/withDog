import api from "./api";

//POST login
export const postLogin = (data) => api.post("/login", data);

//GET logout
export const getLogout = () => api.get("/logout");

//POST signup
export const postSignUp = (data) => api.post("/members", data);

//GET refreshToken
export const getRefreshToken = () => api.get("/refresh-token");

//GET memberInfo
export const getMemberInfo = (accessToken) => api.get(`/members/info?accessToken=${accessToken}`);