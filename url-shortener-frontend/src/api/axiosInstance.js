import axios from "axios";
import { isTokenValid, logout } from "./authApi";
const BASE_URL = `${import.meta.env.VITE_API_URL}/api`;
const axiosInstance = axios.create({
  baseURL: BASE_URL
});

axiosInstance.interceptors.request.use(
  (config) => {
    console.log("Axios Interseptor");
    const token = localStorage.getItem("token");

    if (token) {
      if (!isTokenValid()) {
        logout();
        window.location.href = "/login";
        return Promise.reject("Token expired");
      }

      config.headers.Authorization = "Bearer " + token;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosInstance;