import axios from "axios";

const api = axios.create({
  // aws EC2 Domain
  baseURL: "https://api.withdog.store/api/v1",
  // baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("access_token");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

let isRefreshing = false;

api.interceptors.response.use(
  (response) => response, // 정상응답이라면 그대로 리턴

  async (error) => {
    if (error.response.status === 401 && error.response.data.code === "TE") {
      localStorage.removeItem("access_token");
      if (!isRefreshing) {
        isRefreshing = true;

        const originalRequest = error.config;

        try {
          const refreshResponse = await api.get("/refresh-token");

          const authorizationHeader = refreshResponse.headers["authorization"];

          if (authorizationHeader) {
            const newAccessToken = authorizationHeader.replace("Bearer ", "");
            localStorage.setItem("access_token", newAccessToken);
            originalRequest.headers[
              "Authorization"
            ] = `Bearer ${newAccessToken}`;
          }

          console.log("Access Token 재발급 성공");
          isRefreshing = false;
          return api(originalRequest);
        } catch (refreshError) {
          // refresh token이 만료되었거나 문제가 발생한 경우
          alert("세션이 만료되었습니다. 다시 로그인해주세요.");
          localStorage.clear();
          window.location.href = "/login";
          return;
          // 이미 refresh-token 요청이 진행 중일 경우, 에러 리턴 (대기 방식 Promise 큐 사용이 필요?)
        }
      }
    }
    // 401 외의 다른 에러는 그대로 리턴
    return Promise.reject(error);
  }
);

export default api;
