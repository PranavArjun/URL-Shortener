import axiosInstance from "./axiosInstance";

export const createShortUrl = (data) => {
  return axiosInstance.post("/urls", data);
};

export const getAllUrls = (page = 0) => {
  return axiosInstance.get(`/urls?page=${page}`);
};


export const getMyURLS = (page = 0) => {
  return axiosInstance.get(`/urls/my-urls?page=${page}`);
};