package com.defence.portal.examportal.controller;

import com.defence.portal.examportal.dto.*;
import com.defence.portal.examportal.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    /**
     * 1) GET /api/exams
     * Fetch all active exams
     */
    @GetMapping("/exams")
    public ResponseEntity<List<ExamDTO>> getActiveExams() {
        return ResponseEntity.ok(examService.getActiveExams());
    }

    /**
     * 2) GET /api/exams/{examId}
     * Fetch exam details
     */
    @GetMapping("/exams/{examId}")
    public ResponseEntity<ExamDTO> getExamDetails(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getExamDetails(examId));
    }

    /**
     * 3) POST /api/exams/{examId}/start
     * Start exam attempt
     * Login required. User ID retrieved from token.
     */
    @PostMapping("/exams/{examId}/start")
    public ResponseEntity<Long> startExam(@PathVariable Long examId) {
        Long attemptId = examService.startExam(examId);
        return ResponseEntity.ok(attemptId);
    }

    /**
     * 4) GET /api/attempts/{attemptId}/questions
     * Fetch questions for an active attempt
     */
    @GetMapping("/attempts/{attemptId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsForAttempt(@PathVariable Long attemptId) {
        return ResponseEntity.ok(examService.getQuestionsForAttempt(attemptId));
    }

    /**
     * 5) POST /api/attempts/{attemptId}/answers
     * Submit a single answer
     * Input: questionId, selectedOptionKey
     */
    @PostMapping("/attempts/{attemptId}/answers")
    public ResponseEntity<Void> submitAnswer(@PathVariable Long attemptId,
            @RequestBody SubmitAnswerRequestDTO request) {
        examService.submitAnswer(attemptId, request.getQuestionId(), request.getSelectedOptionKey());
        return ResponseEntity.ok().build();
    }

    /**
     * 6) POST /api/attempts/{attemptId}/submit
     * Submit exam and generate result
     */
    @PostMapping("/attempts/{attemptId}/submit")
    public ResponseEntity<ExamResultDTO> submitExam(@PathVariable Long attemptId) {
        return ResponseEntity.ok(examService.submitExam(attemptId));
    }

    /**
     * 7) GET /api/attempts/{attemptId}/result
     * Fetch exam result
     */
    @GetMapping("/attempts/{attemptId}/result")
    public ResponseEntity<ExamResultDTO> getExamResult(@PathVariable Long attemptId) {
        return ResponseEntity.ok(examService.getExamResult(attemptId));
    }
}
