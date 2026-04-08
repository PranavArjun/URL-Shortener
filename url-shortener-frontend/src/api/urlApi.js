import axios from "axios";

const BASE_URL = "http://localhost:8080/api/urls";

export const createShortUrl = (data)=>{
    return axios.post(BASE_URL,data);
}