import axios from "axios";

export const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

axiosInstance.interceptors.request.use(
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

axiosInstance.interceptors.response.use(
  (response) => response, // 정상응답이라면 그대로 리턴

  async (error) => {
    if (error.response.status === 401 && error.response.data.code === "TE") {
      localStorage.removeItem("access_token");
      const originalRequest = error.config;

      try {
        const refreshResponse = await axiosInstance.get("/refresh-token");

        const authorizationHeader = refreshResponse.headers["authorization"];

        if (authorizationHeader) {
          const newAccessToken = authorizationHeader.replace("Bearer ", "");
          localStorage.setItem("access_token", newAccessToken);
          originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
        }
        console.log("refresh token issue");
        return axiosInstance(originalRequest);
      } catch (refreshError) {
        // refresh token이 만료되었거나 문제가 발생한 경우
        return Promise.reject(refreshError);
      }
    }
    // 401 외의 다른 에러는 그대로 리턴
    return Promise.reject(error);
  }
);
