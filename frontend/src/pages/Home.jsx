import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const Home = () => {
  const { user } = useAuth();

  return (
    <div className="min-h-screen bg-gray-100">

      {/* HERO */}

      <div className="max-w-7xl mx-auto px-6 py-20 text-center">

        <h1 className="text-5xl font-bold text-gray-900 mb-4">
          DefencePortal
        </h1>

        <p className="text-gray-500 text-lg max-w-2xl mx-auto mb-10">
          Your platform for defence exam notifications, study resources,
          and career guidance.
        </p>


        {/* CTA BUTTONS */}

        <div className="flex justify-center gap-4 flex-wrap">

          {user ? (
            <>
              <Link
                to="/notifications"
                className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg font-medium"
              >
                View Notifications
              </Link>

              <span className="text-gray-500 flex items-center">
                Welcome back, {user.username}
              </span>
            </>
          ) : (
            <>
              <Link
                to="/login"
                className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg font-medium"
              >
                Login
              </Link>

              <Link
                to="/signup"
                className="border border-gray-300 px-6 py-3 rounded-lg font-medium hover:bg-gray-50"
              >
                Sign Up
              </Link>
            </>
          )}

        </div>

      </div>


      {/* FEATURES */}

      <div className="max-w-7xl mx-auto px-6 pb-20">

        <h2 className="text-3xl font-semibold text-gray-900 text-center mb-12">
          Platform Features
        </h2>


        <div className="grid md:grid-cols-3 gap-6">


          {/* CARD 1 */}

          <div className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm hover:shadow-md transition">

            <h3 className="text-lg font-semibold text-gray-900 mb-2">
              Exam Notifications
            </h3>

            <p className="text-gray-500">
              Stay updated with defence exam notifications,
              schedules, and results.
            </p>

          </div>


          {/* CARD 2 */}

          <div className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm hover:shadow-md transition">

            <h3 className="text-lg font-semibold text-gray-900 mb-2">
              Study Resources
            </h3>

            <p className="text-gray-500">
              Access preparation materials and mock tests
              for defence examinations.
            </p>

          </div>


          {/* CARD 3 */}

          <div className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm hover:shadow-md transition">

            <h3 className="text-lg font-semibold text-gray-900 mb-2">
              Career Guidance
            </h3>

            <p className="text-gray-500">
              Learn about eligibility, application processes,
              and defence career paths.
            </p>

          </div>

        </div>

      </div>


      {/* FOOTER */}

      <footer className="border-t border-gray-200 py-6 text-center text-gray-500 text-sm">
        © 2025 DefencePortal
      </footer>

    </div>
  );
};

export default Home;