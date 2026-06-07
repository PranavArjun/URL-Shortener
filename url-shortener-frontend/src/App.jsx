import { Routes, Route } from "react-router-dom"
import Navbar from "./components/Navbar"
import Home from "./pages/Home"
import Register from "./pages/Register"
import Login from "./pages/Login"
import MyURLs from "./pages/MyURLs"
import { useEffect } from "react"
import { isTokenValid, logout } from "./api/authApi"


function App() {
  useEffect(() => {
    if (!isTokenValid()) {
      logout();
    }
  }, []);
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/my-urls" element={<MyURLs />} />
      </Routes>
    </>
  )
}

export default App
