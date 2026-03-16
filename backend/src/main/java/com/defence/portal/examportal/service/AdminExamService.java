package com.defence.portal.examportal.service;

import com.defence.portal.examportal.dto.CreateExamRequestDTO;
import com.defence.portal.examportal.dto.CreateOptionRequestDTO;
import com.defence.portal.examportal.dto.CreateQuestionRequestDTO;
import com.defence.portal.examportal.dto.UpdateExamRequestDTO;
import com.defence.portal.examportal.dto.UpdateOptionRequestDTO;
import com.defence.portal.examportal.dto.UpdateQuestionRequestDTO;

public interface AdminExamService {
    Long createExam(CreateExamRequestDTO request);

    Long addQuestionToExam(Long examId, CreateQuestionRequestDTO request);

    void addOptionToQuestion(Long questionId, CreateOptionRequestDTO request);

    // Update Operations
    void updateExam(Long examId, UpdateExamRequestDTO request);

    void patchExam(Long examId, UpdateExamRequestDTO request);

    void updateQuestion(Long questionId, UpdateQuestionRequestDTO request);

    void patchQuestion(Long questionId, UpdateQuestionRequestDTO request);

    void updateOption(Long optionId, UpdateOptionRequestDTO request);

    void patchOption(Long optionId, UpdateOptionRequestDTO request);
}
