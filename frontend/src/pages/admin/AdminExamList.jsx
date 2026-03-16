import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Plus, Search, Edit2, Trash2, Eye, Filter } from "lucide-react";

import AdminSidebar from "../../components/admin/AdminSidebar";
import examService from "../../services/exam.service";

const AdminExamList = () => {
  const [exams, setExams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    fetchExams();
  }, []);

  const fetchExams = async () => {
    try {
      const response = await examService.getActiveExams();
      setExams(response.data);
    } catch (error) {
      console.error("Error fetching exams:", error);
    } finally {
      setLoading(false);
    }
  };

  const filteredExams = exams.filter(
    (exam) =>
      exam.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      exam.category.toLowerCase().includes(searchTerm.toLowerCase()),
  );

  return (
    <div className="flex min-h-screen bg-gray-100">
      <AdminSidebar />

      <main className="flex-1 p-8">
        <div className="max-w-7xl mx-auto">
          {/* Header */}

          <header className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
            <div>
              <h1 className="text-3xl font-semibold text-gray-900">
                Manage Exams
              </h1>

              <p className="text-gray-500 mt-1">
                Create, edit, and organize mock tests.
              </p>
            </div>

            <Link
              to="/admin/exams/new"
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2.5 rounded-lg font-medium flex items-center gap-2 transition w-fit"
            >
              <Plus size={18} />
              New Exam
            </Link>
          </header>

          {/* Table Card */}

          <div className="bg-white border border-gray-200 rounded-xl overflow-hidden shadow-sm">
            {/* Table Controls */}

            <div className="p-4 border-b border-gray-200 flex flex-col md:flex-row gap-4 justify-between items-center bg-gray-50">
              <div className="relative w-full md:w-96">
                <Search
                  className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400"
                  size={18}
                />

                <input
                  type="text"
                  placeholder="Search exams..."
                  className="w-full border border-gray-300 rounded-lg py-2 pl-10 pr-4 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
              </div>

              <button className="flex items-center gap-2 px-4 py-2 bg-gray-100 border border-gray-300 rounded-lg text-gray-600 hover:bg-gray-200 text-sm">
                <Filter size={16} />
                Filters
              </button>
            </div>

            {/* Table */}

            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead>
                  <tr className="bg-gray-50 text-gray-500 text-sm uppercase">
                    <th className="px-6 py-4 font-semibold">Exam Title</th>
                    <th className="px-6 py-4 font-semibold">Category</th>
                    <th className="px-6 py-4 font-semibold">Duration</th>
                    <th className="px-6 py-4 font-semibold">Total Marks</th>
                    <th className="px-6 py-4 font-semibold">Status</th>
                    <th className="px-6 py-4 font-semibold text-right">
                      Actions
                    </th>
                  </tr>
                </thead>

                <tbody className="divide-y divide-gray-200">
                  {loading ? (
                    <tr>
                      <td
                        colSpan="6"
                        className="px-6 py-12 text-center text-gray-500"
                      >
                        <div className="flex justify-center items-center gap-3">
                          <div className="w-5 h-5 border-2 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
                          Loading exams...
                        </div>
                      </td>
                    </tr>
                  ) : filteredExams.length === 0 ? (
                    <tr>
                      <td
                        colSpan="6"
                        className="px-6 py-12 text-center text-gray-500"
                      >
                        No exams found.
                      </td>
                    </tr>
                  ) : (
                    filteredExams.map((exam) => (
                      <tr
                        key={exam.id}
                        className="hover:bg-gray-50 transition group"
                      >
                        <td className="px-6 py-4">
                          <div className="flex items-center gap-3">
                            <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center text-blue-600 font-bold">
                              {exam.title.charAt(0)}
                            </div>

                            <span className="text-gray-900 font-medium">
                              {exam.title}
                            </span>
                          </div>
                        </td>

                        <td className="px-6 py-4 text-gray-500">
                          {exam.category}
                        </td>

                        <td className="px-6 py-4 text-gray-500">
                          {exam.duration} mins
                        </td>

                        <td className="px-6 py-4 text-gray-500">
                          {exam.totalMarks}
                        </td>

                        <td className="px-6 py-4">
                          <span className="px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-600">
                            Active
                          </span>
                        </td>

                        <td className="px-6 py-4 text-right">
                          <div className="flex items-center justify-end gap-2 opacity-0 group-hover:opacity-100 transition">
                            <Link
                              to={`/admin/exams/${exam.id}/questions`}
                              className="p-2 text-gray-500 hover:text-blue-600 hover:bg-gray-100 rounded-lg"
                              title="Manage Questions"
                            >
                              <Eye size={18} />
                            </Link>

                            <Link
                              to={`/admin/exams/edit/${exam.id}`}
                              className="p-2 text-gray-500 hover:text-blue-600 hover:bg-gray-100 rounded-lg"
                              title="Edit Exam"
                            >
                              <Edit2 size={18} />
                            </Link>

                            <button
                              className="p-2 text-gray-500 hover:text-red-600 hover:bg-gray-100 rounded-lg"
                              title="Delete Exam"
                            >
                              <Trash2 size={18} />
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>

            {/* Footer */}

            <div className="p-4 border-t border-gray-200 flex justify-between items-center bg-gray-50">
              <span className="text-sm text-gray-500">
                Showing {filteredExams.length} exams
              </span>

              <div className="flex gap-2">
                <button
                  disabled
                  className="px-3 py-1 rounded border border-gray-200 text-gray-400 cursor-not-allowed"
                >
                  Prev
                </button>

                <button className="px-3 py-1 rounded border border-gray-300 text-gray-600 hover:bg-gray-100">
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default AdminExamList;
