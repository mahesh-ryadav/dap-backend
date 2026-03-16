import { useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const { user, logout } = useAuth();

  return (
    <nav className="bg-white border-b border-gray-200">

      <div className="max-w-7xl mx-auto px-6">

        <div className="flex justify-between items-center h-16">

          {/* LOGO */}

          <Link
            to="/"
            className="text-xl font-semibold text-gray-900"
          >
            DefencePortal
          </Link>


          {/* DESKTOP MENU */}

          <div className="hidden md:flex items-center gap-6 text-sm">

            <Link
              to="/"
              className="text-gray-600 hover:text-blue-600"
            >
              Home
            </Link>

            <Link
              to="/notifications"
              className="text-gray-600 hover:text-blue-600"
            >
              Notifications
            </Link>

            {/* Mock Tests visible to everyone */}
            <Link
              to="/mock-tests"
              className="text-gray-600 hover:text-blue-600"
            >
              Mock Tests
            </Link>

            {user && user.roles.includes("ROLE_ADMIN") && (
              <Link
                to="/admin/dashboard"
                className="text-blue-600 font-medium"
              >
                Admin
              </Link>
            )}


            {/* USER SECTION */}

            {user ? (
              <>
                <span className="text-gray-500">
                  {user.username}
                </span>

                <button
                  onClick={logout}
                  className="bg-red-500 hover:bg-red-600 text-white px-4 py-1.5 rounded-lg text-sm"
                >
                  Logout
                </button>
              </>
            ) : (
              <Link
                to="/login"
                className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg text-sm"
              >
                Login
              </Link>
            )}

          </div>


          {/* MOBILE MENU BUTTON */}

          <button
            onClick={() => setIsOpen(!isOpen)}
            className="md:hidden text-gray-700"
          >
            ☰
          </button>

        </div>

      </div>


      {/* MOBILE MENU */}

      {isOpen && (

        <div className="md:hidden border-t border-gray-200 px-6 py-4 space-y-3 bg-white">

          <Link to="/" className="block text-gray-600 hover:text-blue-600">
            Home
          </Link>

          <Link to="/notifications" className="block text-gray-600 hover:text-blue-600">
            Notifications
          </Link>

          {/* Mock Tests visible to everyone */}
          <Link to="/mock-tests" className="block text-gray-600 hover:text-blue-600">
            Mock Tests
          </Link>

          {user && user.roles.includes("ROLE_ADMIN") && (
            <Link to="/admin/dashboard" className="block text-blue-600 font-medium">
              Admin
            </Link>
          )}

          {user ? (
            <>
              <span className="block text-gray-500">
                {user.username}
              </span>

              <button
                onClick={logout}
                className="w-full bg-red-500 hover:bg-red-600 text-white py-2 rounded-lg"
              >
                Logout
              </button>
            </>
          ) : (
            <Link
              to="/login"
              className="block text-center bg-blue-600 hover:bg-blue-700 text-white py-2 rounded-lg"
            >
              Login
            </Link>
          )}

        </div>

      )}

    </nav>
  );
};

export default Navbar;