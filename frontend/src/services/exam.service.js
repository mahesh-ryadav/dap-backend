import apiClient from './apiClient';

const EXAM_API = '/exams';
const ATTEMPT_API = '/attempts';

// Exam info
export const getActiveExams = () => {
    return apiClient.get(EXAM_API);
};

export const getExamDetails = (examId) => {
    return apiClient.get(`${EXAM_API}/${examId}`);
};

// Exam execution
export const startExam = (examId) => {
    return apiClient.post(`${EXAM_API}/${examId}/start`);
};

export const getQuestionsForAttempt = (attemptId) => {
    return apiClient.get(`${ATTEMPT_API}/${attemptId}/questions`);
};

export const submitAnswer = (attemptId, questionId, selectedOptionKey) => {
    return apiClient.post(`${ATTEMPT_API}/${attemptId}/answers`, {
        attemptId,
        questionId,
        selectedOptionKey,
    });
};

export const submitExam = (attemptId) => {
    return apiClient.post(`${ATTEMPT_API}/${attemptId}/submit`);
};

export const getExamResult = (attemptId) => {
    return apiClient.get(`${ATTEMPT_API}/${attemptId}/result`);
};

const examService = {
    getActiveExams,
    getExamDetails,
    startExam,
    getQuestionsForAttempt,
    submitAnswer,
    submitExam,
    getExamResult,
};

export default examService;
