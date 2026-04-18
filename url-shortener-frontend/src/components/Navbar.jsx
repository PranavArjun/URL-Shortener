import { jwtDecode } from "jwt-decode";
import { Link, useNavigate } from "react-router-dom";

function Navbar() {
    const token = localStorage.getItem("token");
    let role = null;
    if(token){
        const decodedToken = jwtDecode(token);
        role= decodedToken.role;
    }
    const navigate = useNavigate();


    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <nav className="bg-gray-900 text-white px-6 py-3 flex justify-between items-center shadow-md">
            {/* Left Logo */}
            <div className="text-xl font-bold cursor-pointer">
                <Link to="/">URL Shortner</Link>
            </div>

            {/* CenterLinks */}
            <div className="space-x-6 hidden md:flex">
               {token &&  <Link to="/my-urls" className="hover:text-blue-400 transition">My URLs</Link> }

               {token && role ==="ROLE_ADMIN" && (<Link to="/admin-dashboard" className="hover:text-blue-400 transition">Admin</Link>)}
            </div>

            {/* Auth Btns */}
            <div className="space-x-3">
                {token ? (
                    <button onClick={handleLogout} className="border px-3 py-1 rounded hover:bg-white hover:text-black transition">
                        LogOut
                    </button>
                ) : (
                    <>
                        <button onClick={()=>navigate("/login")} className="bg-blue-500 px-3 py-1 rounded hover:bg-blue-600 transition">
                            LogIn
                        </button>
                        <button onClick={()=>navigate("/register")} className="bg-blue-500 px-3 py-1 rounded hover:bg-blue-600 transition">
                            Register
                        </button>
                    </>
                )}



            </div>

        </nav>
    );
}

export default Navbar