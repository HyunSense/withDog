import axios from "axios";
import {getRefreshToken} from "./auth";

let accessToken = null;
let refreshQueue = [];
let isRefreshing = false;

export const setAccessToken = (token) => {
    accessToken = token;
};

const api = axios.create({
  // aws EC2 Domain
  baseURL: "https://api.withdog.store/api/v1",

  // local url
  // baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

api.interceptors.request.use(
  (config) => {
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => response, // 정상응답이라면 그대로 리턴

  async (error) => {
    const originalRequest = error.config;
    if (error.response.status === 401 && error.response.data.code === "TE") {
      // 기존에 저장된 AccessToken 초기화
      accessToken = null;
      if (!isRefreshing) {
        isRefreshing = true;

        try {
          const refreshResponse = await getRefreshToken();
          const newAccessToken = refreshResponse.headers[
            "authorization"
          ]?.replace("Bearer ", "");
          accessToken = newAccessToken;
          refreshQueue.forEach((cb) => cb(newAccessToken)); // PENDING 요청에 새 토큰 전달
          isRefreshing = false;
          originalRequest.headers["authorization"] = `Bearer ${newAccessToken}`;

          return api(originalRequest);

        } catch (refreshError) {
          // refresh token이 만료되었거나 문제가 발생한 경우
          alert("세션이 만료되었습니다. 다시 로그인해주세요.");
          window.location.href = "/login";
          return Promise.reject(refreshError);
        }
      } else {
        return new Promise((resolve, reject) => {
          refreshQueue.push((newAccessToken) => {
            if (newAccessToken) {
               originalRequest.headers["authorization"] = `Bearer ${newAccessToken}`;
               resolve(api(originalRequest));
            } else {
              reject(error);
            }
          });
        });
      }
    }
    // 401 외의 다른 에러는 그대로 리턴
    return Promise.reject(error);
  }
);

export default api;
