import api from "./api";

//POST login
export const postLogin = (data) => api.post("/login", data);

//GET logout
export const getLogout = () => api.get("/logout");

//GET memberinfo
export const getMemberInfo = () => api.get("/members/info");

//POST signup
export const postSignUp = (data) => api.post("/members", data);
