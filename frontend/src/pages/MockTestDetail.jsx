import React, { useState, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import examService from "../services/exam.service";
import { useAuth } from "../contexts/AuthContext";

const MockTestDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();

  const [test, setTest] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [starting, setStarting] = useState(false);

  useEffect(() => {
    fetchTestDetail();
  }, [id]);

  const fetchTestDetail = async () => {
    try {
      setLoading(true);
      const response = await examService.getExamDetails(id);
      setTest(response.data);
    } catch (err) {
      setError("Failed to load test details");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleStartTest = async () => {
    if (!user) {
      navigate("/login");
      return;
    }

    try {
      setStarting(true);
      const response = await examService.startExam(id);
      const attemptId = response.data;
      navigate(`/take-test/${attemptId}`);
    } catch (err) {
      setError("Failed to start test attempt");
    } finally {
      setStarting(false);
    }
  };

  /* LOADING */

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <div className="bg-white border border-gray-200 rounded-xl p-8 shadow-sm text-center">
          <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-500">Loading test details...</p>
        </div>
      </div>
    );
  }

  /* ERROR */

  if (error) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center p-4">
        <div className="bg-white border border-red-200 p-8 rounded-xl shadow-sm text-center max-w-md">
          <h2 className="text-red-600 text-xl font-semibold mb-2">Error</h2>
          <p className="text-gray-500 mb-6">{error}</p>
          <Link
            to="/mock-tests"
            className="bg-red-500 hover:bg-red-600 text-white px-6 py-2 rounded-lg"
          >
            Back to Mock Tests
          </Link>
        </div>
      </div>
    );
  }

  if (!test) return null;

  return (
    <div className="min-h-screen bg-gray-100">

      <div className="max-w-6xl mx-auto px-6 py-12">

        {/* BACK LINK */}

        <Link
          to="/mock-tests"
          className="text-blue-600 hover:text-blue-700 text-sm font-medium"
        >
          ← Back to Mock Tests
        </Link>

        {/* HEADER */}

        <div className="mt-6 mb-10">

          <span className="text-xs bg-blue-100 text-blue-600 px-3 py-1 rounded-full">
            {test.examType || "Exam"}
          </span>

          <h1 className="text-4xl font-semibold text-gray-900 mt-3">
            {test.title}
          </h1>

          <p className="text-gray-500 mt-3 max-w-2xl">
            {test.description}
          </p>

        </div>


        <div className="grid lg:grid-cols-3 gap-10">

          {/* LEFT SECTION */}

          <div className="lg:col-span-2 space-y-6">

            {/* INFO CARDS */}

            <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">

              <InfoCard label="Duration" value={`${test.durationMinutes} min`} />
              <InfoCard label="Max Marks" value={test.totalMarks} />
              <InfoCard label="Negative" value={test.negativeMarkingEnabled ? "Yes" : "No"} />
              <InfoCard label="Passing" value={test.passingMarks} />

            </div>


            {/* INSTRUCTIONS */}

            <div className="bg-white border border-gray-200 rounded-xl p-6">

              <h3 className="text-lg font-semibold text-gray-900 mb-4">
                Test Instructions
              </h3>

              <ul className="space-y-3 text-gray-600 text-sm">

                <li>Stable Internet Connection required</li>
                <li>Timer cannot be paused once started</li>
                <li>Auto submission when time expires</li>
                <li>Review answers before final submission</li>
                <li>Negative marking may apply</li>

              </ul>

            </div>

          </div>


          {/* RIGHT ACTION CARD */}

          <div className="bg-white border border-gray-200 rounded-xl p-8 shadow-sm h-fit">

            <div className="text-center mb-6">

              <p className="text-gray-500 text-sm mb-1">Total Marks</p>

              <h2 className="text-4xl font-bold text-gray-900">
                {test.totalMarks}
              </h2>

            </div>


            <div className="space-y-4 mb-6 text-sm">

              <div className="flex justify-between">
                <span className="text-gray-500">Duration</span>
                <span className="font-medium">{test.durationMinutes} min</span>
              </div>

              <div className="flex justify-between">
                <span className="text-gray-500">Passing Marks</span>
                <span className="font-medium">{test.passingMarks}</span>
              </div>

              <div className="flex justify-between">
                <span className="text-gray-500">Attempts</span>
                <span className="font-medium">Unlimited</span>
              </div>

            </div>


            {user ? (
              <button
                onClick={handleStartTest}
                disabled={starting}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-lg font-medium"
              >
                {starting ? "Starting..." : "Start Exam"}
              </button>
            ) : (
              <Link
                to="/login"
                className="block w-full text-center bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-lg font-medium"
              >
                Login to Start
              </Link>
            )}

            <p className="text-xs text-gray-400 text-center mt-4">
              By starting, you agree to exam guidelines.
            </p>

          </div>

        </div>

      </div>

    </div>
  );
};

/* SMALL CARD COMPONENT */

const InfoCard = ({ label, value }) => (
  <div className="bg-white border border-gray-200 rounded-lg p-4 text-center">
    <p className="text-xs text-gray-500 mb-1">{label}</p>
    <p className="font-semibold text-gray-900">{value}</p>
  </div>
);

export default MockTestDetail;