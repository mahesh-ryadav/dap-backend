import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import {
  Plus,
  Trash2,
  ChevronLeft,
  MessageSquare,
  Edit3,
} from "lucide-react";

import AdminSidebar from "../../components/admin/AdminSidebar";
import adminExamService from "../../services/admin/adminExam.service";
import examService from "../../services/exam.service";

const AdminQuestionManager = () => {
  const { id: examId } = useParams();

  const [exam, setExam] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isAddingQuestion, setIsAddingQuestion] = useState(false);

  const [newQuestion, setNewQuestion] = useState({
    questionText: "",
    questionType: "MCQ",
    marks: 1,
    correctAnswerKey: "",
  });

  const [editingQuestionId, setEditingQuestionId] = useState(null);
  const [editQuestion, setEditQuestion] = useState({});
  const [selectedQuestionId, setSelectedQuestionId] = useState(null);
  const [newOption, setNewOption] = useState({ optionKey: "", optionText: "", isCorrect: false });
  const [editingOption, setEditingOption] = useState({});

  useEffect(() => {
    fetchData();
  }, [examId]);

  const fetchData = async () => {
    try {
      const res = await examService.getExamDetails(examId);

      setExam(res.data);
      setQuestions(res.data.questions || []);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const refreshData = () => fetchData();

  const deleteQuestion = async (questionId) => {
    if (!confirm('Are you sure you want to delete this question?')) return;
    try {
      await adminExamService.deleteQuestion(questionId); // Assume service method
      setQuestions(questions.filter(q => q.id !== questionId));
    } catch (error) {
      alert("Failed to delete question");
    }
  };

  const handleAddQuestion = async (e) => {
    e.preventDefault();

    try {
      const res = await adminExamService.addQuestionToExam(
        examId,
        newQuestion
      );

      setQuestions([...questions, res.data]);
      setIsAddingQuestion(false);

      setNewQuestion({
        questionText: "",
        questionType: "MCQ",
        marks: 1,
        correctAnswerKey: "",
      });
    } catch (error) {
      alert("Failed to add question");
    }
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      
      <AdminSidebar />

      <main className="flex-1 p-8">

        {/* Header */}

        <div className="max-w-6xl mx-auto">

          <Link
            to="/admin/exams"
            className="flex items-center gap-2 text-gray-600 hover:text-black mb-6"
          >
            <ChevronLeft size={18} />
            Back to Exams
          </Link>

          <div className="flex justify-between items-center mb-8">

            <div>
              <h1 className="text-3xl font-bold">
                {exam?.title || "Exam Questions"}
              </h1>

              <p className="text-gray-500">
                {questions.length} Questions
              </p>
            </div>

            <button
              onClick={() => setIsAddingQuestion(true)}
              className="flex items-center gap-2 bg-blue-600 text-white px-5 py-2 rounded-lg hover:bg-blue-700"
            >
              <Plus size={18} />
              Add Question
            </button>

          </div>

          {/* Add Question Form */}

          {isAddingQuestion && (

            <div className="bg-white rounded-xl shadow p-6 mb-8">

              <h3 className="text-lg font-semibold mb-4">
                Add New Question
              </h3>

              <form onSubmit={handleAddQuestion} className="space-y-4">

                <textarea
                  required
                  placeholder="Enter question"
                  className="w-full border rounded-lg p-3"
                  value={newQuestion.questionText}
                  onChange={(e) =>
                    setNewQuestion({
                      ...newQuestion,
                      questionText: e.target.value,
                    })
                  }
                />

                <div className="grid grid-cols-3 gap-4">

                  <input
                    type="number"
                    placeholder="Marks"
                    className="border rounded-lg p-2"
                    value={newQuestion.marks}
                    onChange={(e) =>
                      setNewQuestion({
                        ...newQuestion,
                        marks: parseInt(e.target.value),
                      })
                    }
                  />

                  <input
                    type="text"
                    placeholder="Question Type"
                    className="border rounded-lg p-2"
                    value={newQuestion.questionType}
                    onChange={(e) =>
                      setNewQuestion({
                        ...newQuestion,
                        questionType: e.target.value,
                      })
                    }
                  />

                  <input
                    type="text"
                    placeholder="Correct Answer (A/B/C/D)"
                    className="border rounded-lg p-2"
                    value={newQuestion.correctAnswerKey}
                    onChange={(e) =>
                      setNewQuestion({
                        ...newQuestion,
                        correctAnswerKey: e.target.value,
                      })
                    }
                  />

                </div>

                <div className="flex gap-3">

                  <button
                    type="submit"
                    className="bg-green-600 text-white px-4 py-2 rounded-lg"
                  >
                    Save
                  </button>

                  <button
                    type="button"
                    onClick={() => setIsAddingQuestion(false)}
                    className="bg-gray-200 px-4 py-2 rounded-lg"
                  >
                    Cancel
                  </button>

                </div>

              </form>

            </div>
          )}

          {/* Question List */}

          {loading ? (
            <div className="text-center py-10 text-gray-500">
              Loading...
            </div>
          ) : questions.length === 0 ? (

            <div className="bg-white rounded-xl shadow p-10 text-center">

              <MessageSquare size={40} className="mx-auto text-gray-400 mb-4" />

              <h3 className="text-lg text-gray-500">
                No questions added yet
              </h3>

            </div>

          ) : (

            <div className="space-y-4">

              {questions.map((q, index) => (

                <div
                  key={q.id}
                  className="bg-white rounded-xl shadow p-6"
                >

                  <div className="flex justify-between items-start mb-3">

                    <div className="font-semibold">
                      Question {index + 1}
                    </div>

                    <div className="text-sm text-gray-500">
                      {q.marks || 1} Marks
                    </div>

                  </div>

                  <p className="text-gray-800 mb-4">
                    {q.content}
                  </p>

                  {/* Options */}

                  <div className="grid grid-cols-2 gap-3 mb-4">

                    {(q.options || []).map((opt, i) => (

                      <div
                        key={i}
                        className={`border rounded-lg p-2 text-sm ${
                          opt.isCorrect
                            ? "bg-green-50 border-green-400"
                            : "bg-gray-50"
                        }`}
                      >
                        {opt.text}
                      </div>

                    ))}

                  </div>

                  <div className="flex gap-4 justify-end">

                    <button className="flex items-center gap-2 text-blue-600">
                      <Edit3 size={16} />
                      Edit
                    </button>

                    <button className="flex items-center gap-2 text-red-600">
                      <Trash2 size={16} />
                      Delete
                    </button>

                  </div>

                </div>

              ))}

            </div>
          )}

        </div>
      </main>
    </div>
  );
};

export default AdminQuestionManager;