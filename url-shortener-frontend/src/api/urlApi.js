import axios from "axios";


const BASE_URL = `${import.meta.env.VITE_API_URL}/api/urls`;

export const createShortUrl = (data)=>{
    const token = localStorage.getItem("token");
    return axios.post(BASE_URL,data,
        {
            headers : {
                Authorization:`Bearer ${token}`
            }
        }
    );
}

export const getAllUrls = ()=> {
    return axios.get(BASE_URL);
}

export const getMyURLS = () =>{
    const token = localStorage.getItem("token");
    return axios.get(`${BASE_URL}/my-urls`,{
        headers :{
            Authorization: `Bearer ${token}`
        }
    });
}