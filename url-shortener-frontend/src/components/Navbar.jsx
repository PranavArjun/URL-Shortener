import { Link } from "react-router-dom";

function Navbar(){
    return(
        <nav className="bg-gray-900 text-white px-6 py-3 flex justify-between items-center shadow-md">
            {/* Left Logo */}
            <div className="text-xl font-bold cursor-pointer">
                <Link to="/">URL Shortner</Link>
            </div>

            {/* CenterLinks */}
            <div className="space-x-6 hidden md:flex">
                <Link to="/my-urls" className="hover:text-blue-400 transition">My URLs</Link>
                <Link to="/admin-dashboard" className="hover:text-blue-400 transition">Admin</Link>
            </div>

            {/* Auth Btns */}
            <div className="space-x-3">
                <button className="border px-3 py-1 rounded hover:bg-white hover:text-black transition">
                    Sign-In
                </button>
                <button className="bg-blue-500 px-3 py-1 rounded hover:bg-blue-600 transition">
                    Register
                </button>

            </div>

        </nav>
    );
}

export default Navbar