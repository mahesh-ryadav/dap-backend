import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import {
    Plus,
    Trash2,
    ChevronLeft,
    MessageSquare,
    CheckCircle2,
    XCircle,
    GripVertical,
    Edit3
} from "lucide-react";
import AdminSidebar from "../../components/admin/AdminSidebar";
import adminExamService from "../../services/admin/adminExam.service";
import examService from "../../services/exam.service";
import { motion, AnimatePresence } from "framer-motion";

const AdminQuestionManager = () => {
    const { id: examId } = useParams();
    const [exam, setExam] = useState(null);
    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isAddingQuestion, setIsAddingQuestion] = useState(false);
    const [newQuestion, setNewQuestion] = useState({
        content: "",
        explanation: "",
        marks: 1
    });

    useEffect(() => {
        fetchData();
    }, [examId]);

    const fetchData = async () => {
        try {
            const [examRes, questionsRes] = await Promise.all([
                examService.getExamDetails(examId),
                // Assuming this endpoint exists based on the structure of others
                // If not, we might need a workaround or assume questions come with exam details
                examService.getExamDetails(examId).then(res => ({ data: res.data.questions || [] }))
            ]);
            setExam(examRes.data);
            setQuestions(questionsRes.data);
        } catch (error) {
            console.error("Error fetching question data:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleAddQuestion = async (e) => {
        e.preventDefault();
        try {
            const response = await adminExamService.addQuestionToExam(examId, newQuestion);
            setQuestions([...questions, response.data]);
            setNewQuestion({ content: "", explanation: "", marks: 1 });
            setIsAddingQuestion(false);
        } catch (error) {
            console.error("Error adding question:", error);
            alert("Failed to add question.");
        }
    };

    return (
        <div className="flex min-h-screen bg-slate-950">
            <AdminSidebar />

            <main className="flex-1 p-8">
                <div className="max-w-5xl mx-auto">
                    <Link
                        to="/admin/exams"
                        className="flex items-center gap-2 text-slate-400 hover:text-white mb-6 transition-colors w-fit"
                    >
                        <ChevronLeft size={20} />
                        Back to Exams
                    </Link>

                    <header className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
                        <div>
                            <span className="text-yellow-500 font-semibold text-sm uppercase tracking-wider">Exam Questions</span>
                            <h1 className="text-3xl font-bold text-white mt-1">{exam?.title || "Loading..."}</h1>
                            <p className="text-slate-400 mt-1">{questions.length} Questions Total</p>
                        </div>
                        <button
                            onClick={() => setIsAddingQuestion(true)}
                            className="bg-yellow-500 hover:bg-yellow-400 text-slate-900 px-6 py-2.5 rounded-xl font-bold flex items-center gap-2 transition-all shadow-lg shadow-yellow-500/20 w-fit"
                        >
                            <Plus size={20} />
                            Add Question
                        </button>
                    </header>

                    <div className="space-y-6">
                        <AnimatePresence>
                            {isAddingQuestion && (
                                <motion.div
                                    initial={{ opacity: 0, height: 0 }}
                                    animate={{ opacity: 1, height: "auto" }}
                                    exit={{ opacity: 0, height: 0 }}
                                    className="overflow-hidden"
                                >
                                    <div className="bg-slate-900 border border-yellow-500/30 rounded-2xl p-6 mb-8">
                                        <h3 className="text-lg font-semibold text-white mb-4">New Question</h3>
                                        <form onSubmit={handleAddQuestion} className="space-y-4">
                                            <textarea
                                                required
                                                placeholder="Type your question here..."
                                                className="w-full bg-slate-800 border border-slate-700 rounded-xl py-3 px-4 text-white focus:outline-none focus:border-yellow-500 transition-colors"
                                                value={newQuestion.content}
                                                onChange={(e) => setNewQuestion({ ...newQuestion, content: e.target.value })}
                                            />
                                            <div className="grid grid-cols-2 gap-4">
                                                <input
                                                    type="number"
                                                    placeholder="Marks"
                                                    className="bg-slate-800 border border-slate-700 rounded-xl py-3 px-4 text-white focus:outline-none focus:border-yellow-500 transition-colors"
                                                    value={newQuestion.marks}
                                                    onChange={(e) => setNewQuestion({ ...newQuestion, marks: parseInt(e.target.value) })}
                                                />
                                                <div className="flex gap-2">
                                                    <button
                                                        type="submit"
                                                        className="flex-1 bg-yellow-500 text-slate-900 font-bold rounded-xl hover:bg-yellow-400 transition-colors"
                                                    >
                                                        Save
                                                    </button>
                                                    <button
                                                        type="button"
                                                        onClick={() => setIsAddingQuestion(false)}
                                                        className="px-6 bg-slate-800 text-white font-semibold rounded-xl border border-slate-700 hover:bg-slate-700 transition-colors"
                                                    >
                                                        Cancel
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </motion.div>
                            )}
                        </AnimatePresence>

                        {loading ? (
                            <div className="flex justify-center py-20">
                                <div className="w-10 h-10 border-4 border-yellow-500 border-t-transparent rounded-full animate-spin"></div>
                            </div>
                        ) : questions.length === 0 ? (
                            <div className="text-center py-20 bg-slate-900/30 border border-dashed border-slate-800 rounded-2xl">
                                <MessageSquare size={48} className="text-slate-700 mx-auto mb-4" />
                                <h3 className="text-xl font-medium text-slate-400">No questions added yet</h3>
                                <p className="text-slate-500 mt-2">Click "Add Question" to start building your exam.</p>
                            </div>
                        ) : (
                            questions.map((q, index) => (
                                <motion.div
                                    key={q.id}
                                    initial={{ opacity: 0, x: -20 }}
                                    animate={{ opacity: 1, x: 0 }}
                                    transition={{ delay: index * 0.05 }}
                                    className="bg-slate-900/50 border border-slate-800 rounded-2xl p-6 group hover:border-slate-700 transition-all"
                                >
                                    <div className="flex items-start gap-4">
                                        <div className="p-2 bg-slate-800 text-slate-500 rounded-lg cursor-grab active:cursor-grabbing">
                                            <GripVertical size={18} />
                                        </div>
                                        <div className="flex-1">
                                            <div className="flex justify-between items-start mb-2">
                                                <span className="text-yellow-500 font-bold text-sm">Question {index + 1}</span>
                                                <div className="flex items-center gap-2">
                                                    <span className="text-xs bg-slate-800 text-slate-400 px-2 py-1 rounded border border-slate-700">
                                                        {q.marks || 1} Marks
                                                    </span>
                                                </div>
                                            </div>
                                            <p className="text-white text-lg leading-relaxed mb-6">{q.content}</p>

                                            {/* Options Placeholder */}
                                            <div className="grid grid-cols-1 md:grid-cols-2 gap-3 mb-6">
                                                {(q.options || []).map((opt, oIdx) => (
                                                    <div
                                                        key={oIdx}
                                                        className={`flex items-center gap-3 p-3 rounded-xl border ${opt.isCorrect
                                                                ? "bg-green-500/10 border-green-500/30 text-green-500"
                                                                : "bg-slate-800/50 border-slate-700/50 text-slate-400"
                                                            }`}
                                                    >
                                                        {opt.isCorrect ? <CheckCircle2 size={16} /> : <XCircle size={16} />}
                                                        <span className="text-sm font-medium">{opt.text}</span>
                                                    </div>
                                                ))}
                                            </div>

                                            <div className="flex justify-end gap-3 opacity-0 group-hover:opacity-100 transition-opacity">
                                                <button className="flex items-center gap-2 px-4 py-1.5 bg-slate-800 text-slate-400 hover:text-blue-400 hover:border-blue-400/50 border border-slate-700 rounded-lg transition-all text-sm">
                                                    <Edit3 size={16} />
                                                    Edit
                                                </button>
                                                <button className="flex items-center gap-2 px-4 py-1.5 bg-slate-800 text-slate-400 hover:text-red-400 hover:border-red-400/50 border border-slate-700 rounded-lg transition-all text-sm">
                                                    <Trash2 size={16} />
                                                    Delete
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </motion.div>
                            ))
                        )}
                    </div>
                </div>
            </main>
        </div>
    );
};

export default AdminQuestionManager;
