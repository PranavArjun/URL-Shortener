import axios from "axios";

const BASE_URL = `${import.meta.env.VITE_API_URL}/api/auth`;

export const registerUser = (data)=> axios.post(`${BASE_URL}/register`,data);

export const loginUser = (data)=> axios.post(`${BASE_URL}/login`,data);

export const getToken = () =>
  localStorage.getItem("token");

export const logout = () => {
  localStorage.removeItem("token");
};

export const isTokenValid = () => {
  const token = getToken();

  if (!token) return false;

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return Date.now() < payload.exp * 1000;
  } catch {
    return false;
  }
};