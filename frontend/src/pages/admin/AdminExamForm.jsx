import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Save, X, Clock, Award, BookOpen, Info } from "lucide-react";

import AdminSidebar from "../../components/admin/AdminSidebar";
import adminExamService from "../../services/admin/adminExam.service";
import examService from "../../services/exam.service";

const AdminExamForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEditMode = !!id;

const [formData, setFormData] = useState({
    title: "",
    description: "",
    durationMinutes: 60,
    totalMarks: 100,
    passingMarks: 40,
    negativeMarkingEnabled: false,
    negativeMarksPerQuestion: 0.25,
    active: true,
    category: "",
  });

  const [loading, setLoading] = useState(false);
  const [fetching, setFetching] = useState(isEditMode);

  useEffect(() => {
    if (isEditMode) {
      fetchExamDetails();
    }
  }, [id]);

  const fetchExamDetails = async () => {
    try {
      const response = await examService.getExamDetails(id);
      setFormData(response.data);
    } catch (error) {
      console.error("Error fetching exam details:", error);
      alert("Failed to fetch exam details.");
    } finally {
      setFetching(false);
    }
  };

const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    const val = type === "checkbox" ? checked : (["durationMinutes", "totalMarks", "passingMarks", "negativeMarksPerQuestion"].includes(name) ? parseFloat(value) || 0 : value);
    setFormData((prev) => ({ ...prev, [name]: val }));
  };



  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (isEditMode) {
        await adminExamService.updateExam(id, formData);
      } else {
        await adminExamService.createExam(formData);
      }

      navigate("/admin/exams");
    } catch (error) {
      console.error("Error saving exam:", error);
      alert("Failed to save exam.");
    } finally {
      setLoading(false);
    }
  };

  /* Loading */

  if (fetching) {
    return (
      <div className="flex min-h-screen bg-gray-100">
        <AdminSidebar />
        <div className="flex-1 flex items-center justify-center">
          <div className="w-10 h-10 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
        </div>
      </div>
    );
  }

  return (
    <div className="flex min-h-screen bg-gray-100">
      <AdminSidebar />

      <main className="flex-1 p-8">
        <div className="max-w-3xl mx-auto">
          {/* Header */}

          <header className="flex items-center justify-between mb-8">
            <div>
              <h1 className="text-3xl font-semibold text-gray-900">
                {isEditMode ? "Edit Exam" : "Create New Exam"}
              </h1>

              <p className="text-gray-500 mt-1">
                {isEditMode
                  ? "Update exam settings and information."
                  : "Configure a new mock test for candidates."}
              </p>
            </div>

            <button
              onClick={() => navigate("/admin/exams")}
              className="p-2 text-gray-500 hover:text-gray-900 hover:bg-gray-200 rounded-lg transition"
            >
              <X size={22} />
            </button>
          </header>

          {/* Form Card */}

          <div className="bg-white border border-gray-200 rounded-xl p-8 shadow-sm">
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Exam Title */}

              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700 flex items-center gap-2">
                  <BookOpen size={16} className="text-blue-600" />
                  Exam Title
                </label>

                <input
                  type="text"
                  name="title"
                  required
                  placeholder="e.g., NDA 2024 Mathematics Mock"
                  className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-900 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  value={formData.title}
                  onChange={handleChange}
                />
              </div>

              {/* Duration + Marks */}

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                <div className="space-y-2">
                  <label className="text-sm font-medium text-gray-700 flex items-center gap-2">
                    <Clock size={16} className="text-blue-600" />
                    Duration (Minutes)
                  </label>
                  <input
                    type="number"
                    name="durationMinutes"
                    required
                    min="1"
                    className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-900 focus:ring-2 focus:ring-blue-500"
                    value={formData.durationMinutes}
                    onChange={handleChange}
                  />
                </div>

                <div className="space-y-2">
                  <label className="text-sm font-medium text-gray-700 flex items-center gap-2">
                    <Award size={16} className="text-green-600" />
                    Total Marks
                  </label>

              <input
                  type="number"
                  name="totalMarks"
                  required
                  min="1"
                  className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-900 focus:ring-2 focus:ring-blue-500"
                  value={formData.totalMarks}
                  onChange={handleChange}
                />

                <div className="space-y-2">
                  <label className="text-sm font-medium text-gray-700 flex items-center gap-2">
                    <Award size={16} className="text-green-600" />
                    Passing Marks
                  </label>
                  <input
                    type="number"
                    name="passingMarks"
                    className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-900 focus:ring-2 focus:ring-blue-500"
                    value={formData.passingMarks}
                    onChange={handleChange}
                  />
                </div>
                </div>
              </div>

              {/* Negative Marking */}
              <fieldset className="space-y-3 p-4 border border-gray-200 rounded-lg bg-gray-50">
                <legend className="text-sm font-medium text-gray-700 px-1">Negative Marking</legend>
                <label className="flex items-center gap-3">
                  <input
                    type="checkbox"
                    name="negativeMarkingEnabled"
                    className="w-5 h-5 text-red-600 rounded"
                    checked={formData.negativeMarkingEnabled}
                    onChange={handleChange}
                  />
                  <span className="text-sm font-medium text-gray-900">Enable Negative Marking</span>
                </label>
                {formData.negativeMarkingEnabled && (
                  <div className="ml-8">
                    <label className="text-xs text-gray-600 block mb-1">Marks per wrong answer</label>
                    <input
                      type="number"
                      name="negativeMarksPerQuestion"
                      step="0.25"
                      min="0"
                      max="1"
                      className="w-full max-w-xs border border-gray-300 rounded-lg py-2 px-3 text-gray-900 focus:ring-2 focus:ring-red-500"
                      value={formData.negativeMarksPerQuestion}
                      onChange={handleChange}
                    />
                  </div>
                )}
              </fieldset>

              {/* Active Status */}
              <div className="space-y-2">
                <label className="flex items-center gap-3 p-3 border border-gray-300 rounded-lg bg-green-50 hover:bg-green-100 transition">
                  <input
                    type="checkbox"
                    name="active"
                    className="w-5 h-5 text-green-600 rounded"
                    checked={formData.active}
                    onChange={handleChange}
                  />
                  <span className="text-sm font-medium text-gray-900">
                    Exam is Active / Live for students
                  </span>
                </label>
              </div>

              {/* Category */}

              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700 flex items-center gap-2">
                  <Info size={16} className="text-blue-600" />
                  Category
                </label>

                <select
                  name="category"
                  className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-900 focus:ring-2 focus:ring-blue-500"
                  value={formData.category}
                  onChange={handleChange}
                >
                  <option value="">Select Category</option>
                  <option value="NDA">NDA</option>
                  <option value="CDS">CDS</option>
                  <option value="AFCAT">AFCAT</option>
                  <option value="INET">INET</option>
                  <option value="OTHER">Other</option>
                </select>
              </div>

              {/* Description */}

              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700">
                  Description
                </label>

                <textarea
                  name="description"
                  rows="4"
                  className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-900 focus:ring-2 focus:ring-blue-500"
                  placeholder="Provide a brief overview of the exam..."
                  value={formData.description}
                  onChange={handleChange}
                ></textarea>
              </div>

              {/* Buttons */}

              <div className="pt-4 flex gap-4">
                <button
                  type="submit"
                  disabled={loading}
                  className="flex-1 bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-lg font-medium flex items-center justify-center gap-2 transition"
                >
                  {loading ? (
                    <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                  ) : (
                    <Save size={18} />
                  )}

                  {isEditMode ? "Update Exam" : "Create Exam"}
                </button>

                <button
                  type="button"
                  onClick={() => navigate("/admin/exams")}
                  className="px-8 bg-gray-200 hover:bg-gray-300 text-gray-800 rounded-lg font-medium"
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default AdminExamForm;
