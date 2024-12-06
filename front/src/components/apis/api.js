import axios from "axios";

//json. proxy 추가하는것과 차이?
const api = axios.create({
  baseURL: "http://192.168.0.5:8080",
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

api.interceptors.response.use(
  
  (response) => response, // 정상응답이라면 그대로 리턴

  async (error) => {
    if (error.response.status === 401 && error.response.data.code === "TE") {
      localStorage.removeItem("access_token");
      const originalRequest = error.config;

      try {
        const refreshResponse = await api.get("/refresh-token");

        const authorizationHeader = refreshResponse.headers["authorization"];

        if (authorizationHeader) {
          const newAccessToken = authorizationHeader.replace("Bearer ", "");
          localStorage.setItem("access_token", newAccessToken);
          originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
        }
        console.log("refresh token issue");
        return api(originalRequest);
      } catch (refreshError) {
        // refresh token이 만료되었거나 문제가 발생한 경우
        return Promise.reject(refreshError);
      }
    }
    // 401 외의 다른 에러는 그대로 리턴
    return Promise.reject(error);
  }
);

export default api;
