import apiClient from '../apiClient';

const ADMIN_EXAM_API = '/admin/exams';
const ADMIN_QUESTION_API = '/admin/questions';
const ADMIN_OPTION_API = '/admin/options';

// Exam Management
export const createExam = (examData) => {
    return apiClient.post(ADMIN_EXAM_API, examData);
};

export const updateExam = (examId, examData) => {
    return apiClient.put(`${ADMIN_EXAM_API}/${examId}`, examData);
};

export const patchExam = (examId, examData) => {
    return apiClient.patch(`${ADMIN_EXAM_API}/${examId}`, examData);
};

// Question Management
export const addQuestionToExam = (examId, questionData) => {
    return apiClient.post(`${ADMIN_EXAM_API}/${examId}/questions`, questionData);
};

export const updateQuestion = (questionId, questionData) => {
    return apiClient.put(`${ADMIN_QUESTION_API}/${questionId}`, questionData);
};

export const patchQuestion = (questionId, questionData) => {
    return apiClient.patch(`${ADMIN_QUESTION_API}/${questionId}`, questionData);
};

// Option Management
export const addOptionToQuestion = (questionId, optionData) => {
    return apiClient.post(`${ADMIN_QUESTION_API}/${questionId}/options`, optionData);
};

export const updateOption = (optionId, optionData) => {
    return apiClient.put(`${ADMIN_OPTION_API}/${optionId}`, optionData);
};

export const patchOption = (optionId, optionData) => {
    return apiClient.patch(`${ADMIN_OPTION_API}/${optionId}`, optionData);
};

const adminExamService = {
    createExam,
    updateExam,
    patchExam,
    addQuestionToExam,
    updateQuestion,
    patchQuestion,
    addOptionToQuestion,
    updateOption,
    patchOption,
};

export default adminExamService;
