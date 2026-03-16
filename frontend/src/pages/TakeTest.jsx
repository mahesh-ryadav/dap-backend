import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import examService from '../services/exam.service';

const TakeTest = () => {
  const { attemptId } = useParams();
  const navigate = useNavigate();
  const [attempt, setAttempt] = useState(null);
  const [currentQuestionIdx, setCurrentQuestionIdx] = useState(0);
  const [answers, setAnswers] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [timeLeft, setTimeLeft] = useState(0);
  const [submitting, setSubmitting] = useState(false);
  const [showReview, setShowReview] = useState(false);

  useEffect(() => {
    fetchAttempt();
  }, [attemptId]);

  useEffect(() => {
    if (attempt && attempt.durationMinutes) {
      const endTime = new Date(attempt.startTime).getTime() + (attempt.durationMinutes * 60 * 1000);
      const interval = setInterval(() => {
        const now = new Date().getTime();
        const remaining = Math.max(0, endTime - now);
        setTimeLeft(Math.floor(remaining / 1000));
        if (remaining <= 0) {
          handleSubmitTest();
        }
      }, 1000);
      return () => clearInterval(interval);
    }
  }, [attempt]);

  const fetchAttempt = async () => {
    try {
      setLoading(true);
      const response = await examService.getQuestionsForAttempt(attemptId);
      const questions = response.data;

      // For now, let's assume we need some metadata from the exam itself if not in attempt
      // This is a placeholder for actual attempt metadata retrieval if backend provides it
      setAttempt({
        questions,
        durationMinutes: 60, // Default for now
        startTime: new Date().toISOString()
      });

      const initialAnswers = {};
      questions.forEach(q => {
        initialAnswers[q.id] = '';
      });
      setAnswers(initialAnswers);
    } catch (err) {
      setError('Failed to load test attempt');
      console.error('Error fetching attempt:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleAnswerChange = async (questionId, answer) => {
    setAnswers(prev => ({
      ...prev,
      [questionId]: answer
    }));

    try {
      await examService.submitAnswer(attemptId, questionId, answer);
    } catch (err) {
      console.error('Failed to save answer:', err);
    }
  };

  const handleNext = () => {
    if (currentQuestionIdx < attempt.questions.length - 1) {
      setCurrentQuestionIdx(currentQuestionIdx + 1);
    } else {
      setShowReview(true);
    }
  };

  const handlePrevious = () => {
    if (currentQuestionIdx > 0) {
      setCurrentQuestionIdx(currentQuestionIdx - 1);
    }
  };

  const handleSubmitTest = async () => {
    try {
      setSubmitting(true);
      await examService.submitExam(attemptId);
      navigate(`/test-results/${attemptId}`);
    } catch (err) {
      setError('Failed to submit test');
      console.error('Error submitting test:', err);
    } finally {
      setSubmitting(false);
    }
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins}:${secs.toString().padStart(2, '0')}`;
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-slate-900 flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 border-4 border-yellow-400 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
          <div className="text-white text-xl font-medium">Preparing your exam...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-slate-900 flex items-center justify-center p-4">
        <div className="bg-red-500/10 border border-red-500/20 p-8 rounded-2xl text-center max-w-md">
          <div className="text-red-400 text-5xl mb-4">⚠️</div>
          <div className="text-red-400 text-xl font-bold mb-2">Error</div>
          <div className="text-gray-300 mb-6">{error}</div>
          <button onClick={() => navigate('/mock-tests')} className="bg-red-500 text-white px-6 py-2 rounded-lg font-semibold hover:bg-red-600 transition-colors">
            Back to Mock Tests
          </button>
        </div>
      </div>
    );
  }

  const question = attempt.questions[currentQuestionIdx];
  const progress = ((currentQuestionIdx + 1) / attempt.questions.length) * 100;
  const answeredCount = Object.values(answers).filter(a => a !== '').length;

  return (
    <div className="min-h-screen bg-slate-950 text-white selection:bg-yellow-400/30">
      {/* Background Blobs */}
      <div className="fixed inset-0 overflow-hidden -z-10 pointer-events-none">
        <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-yellow-400/10 rounded-full blur-[120px] animate-pulse"></div>
        <div className="absolute bottom-[-10%] right-[-10%] w-[40%] h-[40%] bg-blue-600/10 rounded-full blur-[120px] animate-pulse" style={{ animationDelay: '2s' }}></div>
      </div>

      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8 h-screen flex flex-col">
        {/* Header - Glassmorphic */}
        <header className="bg-slate-900/40 backdrop-blur-xl border border-white/10 rounded-2xl p-4 sm:p-6 mb-8 flex flex-wrap justify-between items-center gap-4 shadow-2xl">
          <div className="flex items-center gap-4">
            <div className="w-12 h-12 bg-yellow-400 rounded-xl flex items-center justify-center text-slate-900 shadow-[0_0_20px_rgba(250,204,21,0.3)]">
              <svg className="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2.5" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
              </svg>
            </div>
            <div>
              <h1 className="text-xl sm:text-2xl font-bold tracking-tight">Attempt Test</h1>
              <p className="text-gray-400 text-sm">{answeredCount} of {attempt.questions.length} Questions Answered</p>
            </div>
          </div>

          <div className="flex items-center gap-6">
            <div className={`flex flex-col items-end ${timeLeft < 300 ? 'text-red-400 animate-pulse' : 'text-yellow-400'}`}>
              <span className="text-xs font-bold uppercase tracking-widest opacity-60">Time Remaining</span>
              <span className="text-2xl sm:text-3xl font-mono font-black tabular-nums">{formatTime(timeLeft)}</span>
            </div>
          </div>
        </header>

        <div className="flex-1 flex flex-col lg:flex-row gap-8 min-h-0">
          {/* Main Question Area */}
          <main className="flex-1 flex flex-col min-h-0">
            {/* Progress Bar */}
            <div className="w-full h-1.5 bg-white/5 rounded-full mb-8 overflow-hidden">
              <div
                className="h-full bg-gradient-to-r from-yellow-400 to-yellow-600 transition-all duration-500 ease-out shadow-[0_0_10px_rgba(250,204,21,0.5)]"
                style={{ width: `${progress}%` }}
              ></div>
            </div>

            <div className="flex-1 bg-white/5 backdrop-blur-md border border-white/10 rounded-3xl p-6 sm:p-10 flex flex-col overflow-y-auto min-h-0 group relative overflow-hidden">
              <div className="absolute top-0 right-0 p-8 text-8xl font-black text-white/5 pointer-events-none select-none">
                Q{currentQuestionIdx + 1}
              </div>

              <h2 className="text-2xl sm:text-3xl font-semibold mb-10 leading-snug relative z-10">
                {question.questionText}
              </h2>

              <div className="space-y-4 relative z-10">
                {question.options.map((option, idx) => {
                  const isSelected = answers[question.id] === option.optionKey;
                  return (
                    <button
                      key={idx}
                      onClick={() => handleAnswerChange(question.id, option.optionKey)}
                      className={`w-full group/item flex items-center p-5 rounded-2xl border-2 transition-all duration-300 transform hover:translate-x-2 ${isSelected
                          ? 'bg-yellow-400 border-yellow-400 text-slate-900 shadow-[0_10px_25px_-5px_rgba(250,204,21,0.4)]'
                          : 'bg-white/5 border-white/10 text-white hover:bg-white/10 hover:border-white/20'
                        }`}
                    >
                      <div className={`w-10 h-10 rounded-xl flex items-center justify-center font-bold text-lg mr-5 transition-colors ${isSelected ? 'bg-slate-900 text-yellow-400' : 'bg-white/10 text-gray-400 group-hover/item:bg-white/20'
                        }`}>
                        {option.optionKey}
                      </div>
                      <span className="text-lg font-medium text-left">{option.optionText}</span>
                      {isSelected && (
                        <div className="ml-auto">
                          <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                          </svg>
                        </div>
                      )}
                    </button>
                  );
                })}
              </div>

              <div className="mt-auto pt-10 flex justify-between items-center gap-4">
                <button
                  onClick={handlePrevious}
                  disabled={currentQuestionIdx === 0}
                  className="flex items-center gap-2 px-8 py-4 rounded-2xl font-bold transition-all disabled:opacity-20 disabled:cursor-not-allowed text-white hover:bg-white/10 border border-white/10"
                >
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2.5" d="M15 19l-7-7 7-7" />
                  </svg>
                  Previous
                </button>

                <button
                  onClick={handleNext}
                  className="flex items-center gap-2 px-10 py-4 bg-yellow-400 text-slate-900 rounded-2xl font-bold transition-all hover:bg-yellow-300 hover:scale-105 active:scale-95 shadow-xl shadow-yellow-400/20"
                >
                  {currentQuestionIdx === attempt.questions.length - 1 ? 'Review' : 'Next'}
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2.5" d="M9 5l7 7-7 7" />
                  </svg>
                </button>
              </div>
            </div>
          </main>

          {/* Sidebar Question Navigator */}
          <aside className="lg:w-80 flex flex-col">
            <div className="bg-slate-900/40 backdrop-blur-xl border border-white/10 rounded-2xl p-6 h-full shadow-2xl overflow-y-auto min-h-0">
              <h3 className="text-sm font-bold uppercase tracking-widest text-gray-400 mb-6">Question Palette</h3>
              <div className="grid grid-cols-5 gap-3">
                {attempt.questions.map((q, idx) => {
                  const isCurrent = idx === currentQuestionIdx;
                  const isAnswered = answers[q.id] !== '';
                  return (
                    <button
                      key={idx}
                      onClick={() => {
                        setCurrentQuestionIdx(idx);
                        setShowReview(false);
                      }}
                      className={`aspect-square rounded-xl font-bold transition-all duration-300 flex items-center justify-center ${isCurrent
                          ? 'ring-2 ring-yellow-400 bg-yellow-400/20 text-yellow-400 scale-110 shadow-lg'
                          : isAnswered
                            ? 'bg-green-500/20 text-green-400 border border-green-500/30'
                            : 'bg-white/5 text-gray-400 hover:bg-white/10 border border-white/5'
                        }`}
                    >
                      {idx + 1}
                    </button>
                  );
                })}
              </div>

              <div className="mt-10 pt-10 border-t border-white/10">
                <div className="space-y-4 mb-8">
                  <div className="flex items-center gap-3">
                    <div className="w-4 h-4 rounded bg-yellow-400"></div>
                    <span className="text-sm text-gray-400">Current Question</span>
                  </div>
                  <div className="flex items-center gap-3">
                    <div className="w-4 h-4 rounded bg-green-500/20 border border-green-500/30"></div>
                    <span className="text-sm text-gray-400">Answered</span>
                  </div>
                  <div className="flex items-center gap-3">
                    <div className="w-4 h-4 rounded bg-white/5 border border-white/5"></div>
                    <span className="text-sm text-gray-400">Not Visited / Skipped</span>
                  </div>
                </div>

                <button
                  onClick={() => setShowReview(true)}
                  className="w-full py-4 rounded-2xl font-bold bg-white text-slate-900 hover:bg-gray-100 transition-all active:scale-95 shadow-xl"
                >
                  Review & Submit
                </button>
              </div>
            </div>
          </aside>
        </div>
      </div>

      {/* Review Modal */}
      {showReview && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-950/80 backdrop-blur-md">
          <div className="bg-slate-900 border border-white/10 rounded-3xl p-8 max-w-2xl w-full shadow-2xl transform animate-in slide-in-from-bottom-8 duration-500">
            <h2 className="text-3xl font-bold mb-4">Ready to Submit?</h2>
            <p className="text-gray-400 mb-8">Please review your status before finalizing the test. Once submitted, you cannot change your answers.</p>

            <div className="grid grid-cols-2 gap-4 mb-10">
              <div className="bg-white/5 p-6 rounded-2xl border border-white/10">
                <div className="text-sm text-gray-400 uppercase tracking-widest mb-1">Answered</div>
                <div className="text-3xl font-black text-green-400">{answeredCount}</div>
              </div>
              <div className="bg-white/5 p-6 rounded-2xl border border-white/10">
                <div className="text-sm text-gray-400 uppercase tracking-widest mb-1">Pending</div>
                <div className="text-3xl font-black text-red-400">{attempt.questions.length - answeredCount}</div>
              </div>
            </div>

            <div className="flex flex-col sm:flex-row gap-4">
              <button
                onClick={handleSubmitTest}
                disabled={submitting}
                className="flex-1 py-5 bg-gradient-to-r from-yellow-400 to-yellow-600 text-slate-900 rounded-2xl font-black text-xl hover:from-yellow-300 hover:to-yellow-500 transition-all shadow-[0_15px_30px_-5px_rgba(250,204,21,0.4)] disabled:opacity-50"
              >
                {submitting ? 'Submitting...' : 'Confirm Submission'}
              </button>
              <button
                onClick={() => setShowReview(false)}
                className="px-10 py-5 bg-white/5 text-white border border-white/10 rounded-2xl font-bold hover:bg-white/10 transition-all"
              >
                Go Back
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default TakeTest;
