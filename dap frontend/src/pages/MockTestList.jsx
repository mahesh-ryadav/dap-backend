import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import examService from "../services/exam.service";

const MockTestList = () => {
  const [tests, setTests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchTests();
  }, []);

  const fetchTests = async () => {
    try {
      setLoading(true);
      const response = await examService.getActiveExams();
      setTests(response.data);
    } catch (err) {
      setError("Failed to load mock tests");
      console.error("Error fetching tests:", err);
    } finally {
      setLoading(false);
    }
  };

  /* LOADING */

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="bg-white border border-gray-200 rounded-xl p-8 shadow-sm text-center">
          <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-500">Loading mock tests...</p>
        </div>
      </div>
    );
  }

  /* ERROR */

  if (error) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center p-4">
        <div className="bg-white border border-red-200 p-8 rounded-xl shadow-sm text-center max-w-md">
          <div className="text-red-600 text-xl font-semibold mb-2">
            Error
          </div>
          <p className="text-gray-500 mb-6">{error}</p>
          <button
            onClick={fetchTests}
            className="bg-red-500 hover:bg-red-600 text-white px-6 py-2 rounded-lg"
          >
            Try Again
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100">

      {/* HEADER */}

      <div className="max-w-7xl mx-auto px-6 py-16 text-center">

        <h1 className="text-4xl font-semibold text-gray-900 mb-3">
          Mock Exams
        </h1>

        <p className="text-gray-500 max-w-xl mx-auto">
          Practice with our mock tests designed to simulate real defence exam environments.
        </p>

      </div>


      {/* TEST GRID */}

      <div className="max-w-7xl mx-auto px-6 pb-16">

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">

          {tests.map((test) => (

            <div
              key={test.id}
              className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm hover:shadow-md transition"
            >

              {/* STATUS */}

              <div className="flex justify-between items-center mb-4">

                <span className={`text-xs px-3 py-1 rounded-full font-medium 
                  ${test.active ? "bg-green-100 text-green-600" : "bg-gray-200 text-gray-600"}
                `}>
                  {test.active ? "Available" : "Coming Soon"}
                </span>

              </div>


              {/* TITLE */}

              <h2 className="text-lg font-semibold text-gray-900 mb-2">
                {test.title}
              </h2>


              {/* DESCRIPTION */}

              <p className="text-sm text-gray-500 mb-6 line-clamp-2">
                {test.description}
              </p>


              {/* INFO */}

              <div className="grid grid-cols-2 gap-4 mb-6">

                <div className="bg-gray-50 border border-gray-200 rounded-lg p-3 text-center">
                  <p className="text-xs text-gray-500 mb-1">Duration</p>
                  <p className="text-sm font-semibold text-gray-900">
                    {test.durationMinutes} min
                  </p>
                </div>

                <div className="bg-gray-50 border border-gray-200 rounded-lg p-3 text-center">
                  <p className="text-xs text-gray-500 mb-1">Total Marks</p>
                  <p className="text-sm font-semibold text-gray-900">
                    {test.totalMarks}
                  </p>
                </div>

              </div>


              {/* BUTTON */}

              <Link
                to={`/mock-tests/${test.id}`}
                className="block w-full text-center bg-blue-600 hover:bg-blue-700 text-white py-2 rounded-lg font-medium transition"
              >
                View Details
              </Link>

            </div>

          ))}

        </div>

      </div>

    </div>
  );
};

export default MockTestList;