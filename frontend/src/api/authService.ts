import axios from "axios";
import { BASE_URL } from "./apiConfig";

// Create an Axios instance
const axiosInstance = axios.create({
  baseURL: BASE_URL,
});

const axiosInstanceWithCookies = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

// Set up Axios interceptors for handling token expiration and request headers
axiosInstance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor to handle 401 (unauthorized) errors
axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshResponse = await axiosInstanceWithCookies.get(
          "/auth/refresh"
        );
        const newAccessToken = refreshResponse.data.accessToken;

        localStorage.setItem("accessToken", newAccessToken);

        originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
        return axiosInstance(originalRequest);
      } catch (refreshError) {
        console.error("Unable to refresh token:", refreshError);
        localStorage.removeItem("accessToken");

        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

export const authService = {
  loginUser: async (username: string, password: string) => {
    const response = await axiosInstanceWithCookies.post("/auth/login", {
      username,
      password,
    });
    const accessToken = response.data.accessToken;
    const user = response.data.user;

    localStorage.setItem("accessToken", accessToken);

    return user;
  },

  checkAuthStatus: async () => {
    try {
      const response = await axiosInstance.get("/auth/me");
      return response.data;
    } catch (error) {
      console.error("User not authenticated:", error);
      localStorage.removeItem("accessToken");
      localStorage.removeItem("authenticated");
      return null;
    }
  },

  logout: async () => {
    await axiosInstance.post("/auth/logout");
    localStorage.removeItem("accessToken");
  },
};

export { axiosInstance };
