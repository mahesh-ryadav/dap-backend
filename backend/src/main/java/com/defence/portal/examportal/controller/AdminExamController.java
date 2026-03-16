package com.defence.portal.examportal.controller;

import com.defence.portal.examportal.dto.CreateExamRequestDTO;
import com.defence.portal.examportal.dto.CreateOptionRequestDTO;
import com.defence.portal.examportal.dto.CreateQuestionRequestDTO;
import com.defence.portal.examportal.dto.UpdateExamRequestDTO;
import com.defence.portal.examportal.dto.UpdateOptionRequestDTO;
import com.defence.portal.examportal.dto.UpdateQuestionRequestDTO;
import com.defence.portal.examportal.service.AdminExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminExamController {

    private final AdminExamService adminExamService;

    public AdminExamController(AdminExamService adminExamService) {
        this.adminExamService = adminExamService;
    }

    /**
     * 1) Create Exam
     * POST /api/admin/exams
     */
    @PostMapping("/exams")
    public ResponseEntity<Long> createExam(@RequestBody CreateExamRequestDTO request) {
        Long examId = adminExamService.createExam(request);
        return ResponseEntity.ok(examId);
    }

    /**
     * 2) Add Question to Exam
     * POST /api/admin/exams/{examId}/questions
     */
    @PostMapping("/exams/{examId}/questions")
    public ResponseEntity<Long> addQuestionToExam(@PathVariable Long examId,
            @RequestBody CreateQuestionRequestDTO request) {
        Long questionId = adminExamService.addQuestionToExam(examId, request);
        return ResponseEntity.ok(questionId);
    }

    /**
     * 3) Add Option to Question
     * POST /api/admin/questions/{questionId}/options
     */
    @PostMapping("/questions/{questionId}/options")
    public ResponseEntity<Void> addOptionToQuestion(@PathVariable Long questionId,
            @RequestBody CreateOptionRequestDTO request) {
        adminExamService.addOptionToQuestion(questionId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 4) Update Exam (PUT)
     * PUT /api/admin/exams/{examId}
     */
    @PutMapping("/exams/{examId}")
    public ResponseEntity<Void> updateExam(@PathVariable Long examId, @RequestBody UpdateExamRequestDTO request) {
        adminExamService.updateExam(examId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 5) Patch Exam (PATCH)
     * PATCH /api/admin/exams/{examId}
     */
    @PatchMapping("/exams/{examId}")
    public ResponseEntity<Void> patchExam(@PathVariable Long examId, @RequestBody UpdateExamRequestDTO request) {
        adminExamService.patchExam(examId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 6) Update Question (PUT)
     * PUT /api/admin/questions/{questionId}
     */
    @PutMapping("/questions/{questionId}")
    public ResponseEntity<Void> updateQuestion(@PathVariable Long questionId,
            @RequestBody UpdateQuestionRequestDTO request) {
        adminExamService.updateQuestion(questionId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 7) Patch Question (PATCH)
     * PATCH /api/admin/questions/{questionId}
     */
    @PatchMapping("/questions/{questionId}")
    public ResponseEntity<Void> patchQuestion(@PathVariable Long questionId,
            @RequestBody UpdateQuestionRequestDTO request) {
        adminExamService.patchQuestion(questionId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 8) Update Option (PUT)
     * PUT /api/admin/options/{optionId}
     */
    @PutMapping("/options/{optionId}")
    public ResponseEntity<Void> updateOption(@PathVariable Long optionId, @RequestBody UpdateOptionRequestDTO request) {
        adminExamService.updateOption(optionId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * 9) Patch Option (PATCH)
     * PATCH /api/admin/options/{optionId}
     */
    @PatchMapping("/options/{optionId}")
    public ResponseEntity<Void> patchOption(@PathVariable Long optionId, @RequestBody UpdateOptionRequestDTO request) {
        adminExamService.patchOption(optionId, request);
        return ResponseEntity.ok().build();
    }
}
