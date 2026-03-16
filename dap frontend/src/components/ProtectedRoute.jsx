import { Navigate, Link } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const ProtectedRoute = ({ children, requiredRoles }) => {
  const { user, loading } = useAuth();

  /* LOADING */

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100">

        <div className="bg-white border border-gray-200 rounded-xl p-8 shadow-sm text-center">

          <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-blue-600 mx-auto mb-4"></div>

          <p className="text-gray-500">Checking authentication...</p>

        </div>

      </div>
    );
  }

  /* NOT LOGGED IN */

  if (!user) {
    return <Navigate to="/login" />;
  }

  /* ACCESS DENIED */

  if (requiredRoles && !requiredRoles.some(role => user.roles.includes(role))) {
    return (

      <div className="min-h-screen flex items-center justify-center bg-gray-100">

        <div className="bg-white border border-gray-200 rounded-xl p-10 shadow-sm text-center max-w-md">

          <h2 className="text-2xl font-semibold text-red-600 mb-3">
            Access Denied
          </h2>

          <p className="text-gray-500 mb-6">
            You do not have permission to access this page.
          </p>

          <Link
            to="/"
            className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg text-sm"
          >
            Go Home
          </Link>

        </div>

      </div>

    );
  }

  return children;
};

export default ProtectedRoute;