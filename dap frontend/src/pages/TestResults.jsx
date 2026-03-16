import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import examService from '../services/exam.service';

const TestResults = () => {
  const { attemptId } = useParams();
  const [attempt, setAttempt] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchResults();
  }, [attemptId]);

  const fetchResults = async () => {
    try {
      setLoading(true);
      const response = await examService.getExamResult(attemptId);
      setAttempt(response.data);
    } catch (err) {
      setError('Failed to load test results');
      console.error('Error fetching results:', err);
    } finally {
      setLoading(false);
    }
  };

  const getScoreColor = (percentage) => {
    if (percentage >= 80) return 'text-green-400';
    if (percentage >= 60) return 'text-yellow-400';
    return 'text-red-400';
  };

  const getPerformanceMessage = (percentage) => {
    if (percentage >= 80) return 'Legendary Performance! 🏆';
    if (percentage >= 60) return 'Great Job! Keep it up. ✨';
    return 'Good effort! Practice more. 💪';
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-slate-950 flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 border-4 border-yellow-400 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
          <div className="text-white text-xl font-medium">Analysing your performance...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-slate-950 flex items-center justify-center p-4">
        <div className="bg-red-500/10 border border-red-500/20 p-8 rounded-2xl text-center max-w-md">
          <div className="text-red-400 text-5xl mb-4">⚠️</div>
          <div className="text-red-400 text-xl font-bold mb-2">Error</div>
          <div className="text-gray-300 mb-6">{error}</div>
          <Link to="/mock-tests" className="bg-red-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-red-600 transition-colors">
            Back to Mock Tests
          </Link>
        </div>
      </div>
    );
  }

  if (!attempt) return null;

  const score = attempt.finalScore || 0;
  const totalQuestions = attempt.totalQuestions || 0;
  const percentage = totalQuestions > 0 ? Math.round((attempt.correctCount / totalQuestions) * 100) : 0;

  return (
    <div className="min-h-screen bg-slate-950 text-white selection:bg-yellow-400/30">
      {/* Background Blobs */}
      <div className="fixed inset-0 overflow-hidden -z-10 pointer-events-none">
        <div className="absolute top-[20%] right-[-10%] w-[50%] h-[50%] bg-blue-600/10 rounded-full blur-[120px] animate-pulse"></div>
        <div className="absolute bottom-[-10%] left-[-10%] w-[40%] h-[40%] bg-purple-600/10 rounded-full blur-[120px] animate-pulse" style={{ animationDelay: '2s' }}></div>
      </div>

      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        {/* Header */}
        <div className="text-center mb-16 space-y-4">
          <h1 className="text-5xl sm:text-6xl font-black tracking-tight bg-linear-to-r from-white to-white/40 bg-clip-text text-transparent">
            Test Results
          </h1>
          <p className="text-xl text-gray-400 font-medium">Detailed performance breakdown for your attempt</p>
        </div>

        {/* Main Result Card */}
        <div className="bg-white/5 backdrop-blur-2xl border border-white/10 rounded-[2.5rem] p-8 sm:p-12 mb-12 shadow-2xl relative overflow-hidden group">
          {/* Background Accent */}
          <div className={`absolute top-0 left-0 w-full h-2 bg-gradient-to-r ${percentage >= 60 ? 'from-green-400 to-blue-500' : 'from-yellow-400 to-red-500'}`}></div>

          <div className="relative z-10">
            <div className="flex flex-col lg:flex-row gap-12 items-center">
              {/* Circle Score */}
              <div className="relative w-64 h-64 shrink-0">
                <svg className="w-full h-full transform -rotate-90">
                  <circle
                    cx="128"
                    cy="128"
                    r="120"
                    stroke="currentColor"
                    strokeWidth="12"
                    fill="transparent"
                    className="text-white/5"
                  />
                  <circle
                    cx="128"
                    cy="128"
                    r="120"
                    stroke="currentColor"
                    strokeWidth="12"
                    fill="transparent"
                    strokeDasharray={2 * Math.PI * 120}
                    strokeDashoffset={2 * Math.PI * 120 * (1 - percentage / 100)}
                    strokeLinecap="round"
                    className={`${getScoreColor(percentage)} transition-all duration-1000 ease-out`}
                    style={{ filter: 'drop-shadow(0 0 12px currentColor)' }}
                  />
                </svg>
                <div className="absolute inset-0 flex flex-col items-center justify-center text-center">
                  <span className="text-6xl font-black leading-none">{percentage}%</span>
                  <span className="text-gray-400 text-sm font-bold tracking-widest uppercase mt-2">Score</span>
                </div>
              </div>

              <div className="flex-1 text-center lg:text-left">
                <h2 className="text-3xl font-bold mb-2">Performance Summary</h2>
                <p className={`text-2xl font-bold mb-8 ${getScoreColor(percentage)}`}>
                  {getPerformanceMessage(percentage)}
                </p>

                <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
                  {[
                    { label: 'Final Score', value: score, color: 'text-white' },
                    { label: 'Total Questions', value: totalQuestions, color: 'text-gray-400' },
                    { label: 'Correct', value: attempt.correctCount, color: 'text-green-400' },
                    { label: 'Incorrect', value: attempt.incorrectCount, color: 'text-red-400' }
                  ].map((stat, i) => (
                    <div key={i} className="bg-white/5 p-4 rounded-2xl border border-white/5">
                      <div className="text-xs font-bold text-gray-500 uppercase tracking-widest mb-1">{stat.label}</div>
                      <div className={`text-2xl font-black ${stat.color}`}>{stat.value}</div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Detailed Stats */}
        <div className="grid lg:grid-cols-3 gap-8 mb-16">
          <div className="bg-white/5 backdrop-blur-xl border border-white/10 rounded-3xl p-8 shadow-xl">
            <h3 className="text-lg font-bold mb-6 flex items-center gap-3">
              <span className="w-8 h-8 bg-green-400/20 rounded-lg flex items-center justify-center text-green-400">
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2.5" d="M5 13l4 4L19 7" />
                </svg>
              </span>
              Accuracy
            </h3>
            <div className="space-y-4">
              <div className="flex justify-between items-end">
                <span className="text-gray-400">Attempted Questions</span>
                <span className="text-xl font-bold">{attempt.correctCount + attempt.incorrectCount}</span>
              </div>
              <div className="w-full h-2 bg-white/5 rounded-full overflow-hidden">
                <div
                  className="h-full bg-green-400"
                  style={{ width: `${((attempt.correctCount + attempt.incorrectCount) / totalQuestions) * 100}%` }}
                ></div>
              </div>
              <p className="text-sm text-gray-500 leading-relaxed italic">
                You answered {totalQuestions - (attempt.correctCount + attempt.incorrectCount)} questions less than the total available.
              </p>
            </div>
          </div>

          <div className="bg-white/5 backdrop-blur-xl border border-white/10 rounded-3xl p-8 shadow-xl lg:col-span-2">
            <h3 className="text-lg font-bold mb-6 flex items-center gap-3">
              <span className="w-8 h-8 bg-yellow-400/20 rounded-lg flex items-center justify-center text-yellow-400">
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2.5" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </span>
              Insights & Tips
            </h3>
            <div className="grid sm:grid-cols-2 gap-8">
              <div className="space-y-3">
                <h4 className="text-green-400 font-bold text-sm uppercase tracking-widest">Key Strengths</h4>
                <ul className="space-y-2 text-gray-300">
                  <li className="flex gap-2 text-sm"><span className="text-green-500">✓</span> High accuracy on attempted questions</li>
                  <li className="flex gap-2 text-sm"><span className="text-green-500">✓</span> Good focus on core topics</li>
                </ul>
              </div>
              <div className="space-y-3">
                <h4 className="text-red-400 font-bold text-sm uppercase tracking-widest">Growth Areas</h4>
                <ul className="space-y-2 text-gray-300">
                  <li className="flex gap-2 text-sm"><span className="text-red-500">!</span> Increase attempt rate for higher marks</li>
                  <li className="flex gap-2 text-sm"><span className="text-red-500">!</span> Focus on time management</li>
                </ul>
              </div>
            </div>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="flex flex-col sm:flex-row gap-6 justify-center">
          <Link
            to="/mock-tests"
            className="px-12 py-5 bg-yellow-400 text-slate-900 rounded-2xl font-black text-xl hover:bg-yellow-300 hover:scale-105 transition-all shadow-2xl active:scale-95 text-center"
          >
            Start Another Test
          </Link>
          <Link
            to="/"
            className="px-12 py-5 bg-white/5 text-white border border-white/10 rounded-2xl font-bold text-xl hover:bg-white/10 transition-all text-center"
          >
            Go to Dashboard
          </Link>
        </div>
      </div>
    </div>
  );
};

export default TestResults;
