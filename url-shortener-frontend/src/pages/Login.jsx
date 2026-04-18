import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { loginUser } from '../api/authApi';

function Login() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        email: "",
        password: ""
    });

    const [error, setError] = useState("");

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        try {
            const token = await loginUser(formData);
            localStorage.setItem("token",token.data.token);
            console.log(localStorage.getItem("token"))
            navigate("/my-urls");

        } catch (error) {
            setError(
                error.response?.data?.message || "Registration failed"
            );
        }
    };
    return (
        <div className='min-h-screen flex items-center justify-center bg-gray-100 px-4'>
            <form onSubmit={handleSubmit} className='bg-white w-full max-w-md rounded-xl shadow-md p-8 space-y-5'>
                <h2 className='text-3xl font-bold text-center'>LogIn Account</h2>

                <input type='email' name='email' placeholder='Enter email' value={formData.email} onChange={handleChange} className='w-full border px-4 py-2 rounded-lg' required />
                <input type='password' name='password' placeholder='Enter password' value={formData.password} onChange={handleChange} className='w-full border px-4 py-2 rounded-lg' required />

                <button type='submit' className='w-full bg-blue-600 text-white py-2 rounded-lg'>LogIn</button>

                {error && (
                    <p className='text-red-500 text-center text-sm'>{error}</p>
                )}

                <p className='text-center text-sm'>
                    Already have account ? {" "}
                    <Link to="/login" className='text-blue-600'>Login</Link>
                </p>
            </form>

        </div>
    )
}

export default Login
