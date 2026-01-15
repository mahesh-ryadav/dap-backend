package com.defence.portal.examportal.service;

import com.defence.portal.examportal.dto.ExamDTO;
import com.defence.portal.examportal.dto.ExamResultDTO;
import com.defence.portal.examportal.dto.QuestionDTO;

import java.util.List;

public interface ExamService {
    List<ExamDTO> getActiveExams();

    ExamDTO getExamDetails(Long examId);

    Long startExam(Long examId);

    List<QuestionDTO> getQuestionsForAttempt(Long attemptId);

    void submitAnswer(Long attemptId, Long questionId, String selectedOptionKey);

    ExamResultDTO submitExam(Long attemptId);

    ExamResultDTO getExamResult(Long attemptId);
}
