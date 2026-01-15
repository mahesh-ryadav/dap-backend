package com.defence.portal.examportal.service.impl;

import com.defence.portal.examportal.dto.CreateExamRequestDTO;
import com.defence.portal.examportal.dto.CreateOptionRequestDTO;
import com.defence.portal.examportal.dto.CreateQuestionRequestDTO;
import com.defence.portal.examportal.dto.*;
import com.defence.portal.examportal.entity.Exam;
import com.defence.portal.examportal.entity.Option;
import com.defence.portal.examportal.entity.Question;
import com.defence.portal.examportal.exception.ExamPortalException;
import com.defence.portal.examportal.repository.ExamRepository;
import com.defence.portal.examportal.repository.OptionRepository;
import com.defence.portal.examportal.repository.QuestionRepository;
import com.defence.portal.examportal.service.AdminExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminExamServiceImpl implements AdminExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public AdminExamServiceImpl(ExamRepository examRepository,
            QuestionRepository questionRepository,
            OptionRepository optionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    @Transactional
    public Long createExam(CreateExamRequestDTO request) {
        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setPassingMarks(request.getPassingMarks());
        exam.setNegativeMarkingEnabled(request.isNegativeMarkingEnabled());
        exam.setNegativeMarksPerQuestion(request.getNegativeMarksPerQuestion());
        exam.setActive(true); // Default to active
        exam.setCreatedAt(LocalDateTime.now());

        Exam savedExam = examRepository.save(exam);
        return savedExam.getId();
    }

    @Override
    @Transactional
    public Long addQuestionToExam(Long examId, CreateQuestionRequestDTO request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamPortalException("Exam not found"));

        Question question = new Question();
        question.setExam(exam);
        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setMarks(request.getMarks());
        question.setCorrectAnswerKey(request.getCorrectAnswerKey());
        question.setCreatedAt(LocalDateTime.now());

        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    @Override
    @Transactional
    public void addOptionToQuestion(Long questionId, CreateOptionRequestDTO request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ExamPortalException("Question not found"));

        Option option = new Option();
        option.setQuestion(question);
        option.setOptionKey(request.getOptionKey());
        option.setOptionText(request.getOptionText());

        optionRepository.save(option);
    }

    // --- Update Methods (PUT: Full Update) ---

    @Override
    @Transactional
    public void updateExam(Long examId, UpdateExamRequestDTO request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamPortalException("Exam not found"));

        // For PUT, we expect all fields. If null, we might set to null or handle error
        // logic.
        // Usually PUT replaces resource. Assuming strict replacement logic:

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setPassingMarks(request.getPassingMarks());

        if (request.getNegativeMarkingEnabled() != null)
            exam.setNegativeMarkingEnabled(request.getNegativeMarkingEnabled());

        exam.setNegativeMarksPerQuestion(request.getNegativeMarksPerQuestion());

        if (request.getActive() != null)
            exam.setActive(request.getActive());

        examRepository.save(exam);
    }

    @Override
    @Transactional
    public void updateQuestion(Long questionId, UpdateQuestionRequestDTO request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ExamPortalException("Question not found"));

        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setMarks(request.getMarks());
        question.setCorrectAnswerKey(request.getCorrectAnswerKey());

        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void updateOption(Long optionId, UpdateOptionRequestDTO request) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ExamPortalException("Option not found"));

        option.setOptionKey(request.getOptionKey());
        option.setOptionText(request.getOptionText());

        optionRepository.save(option);
    }

    // --- Patch Methods (PATCH: Partial Update) ---

    @Override
    @Transactional
    public void patchExam(Long examId, UpdateExamRequestDTO request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamPortalException("Exam not found"));

        if (request.getTitle() != null)
            exam.setTitle(request.getTitle());
        if (request.getDescription() != null)
            exam.setDescription(request.getDescription());
        if (request.getDurationMinutes() != null)
            exam.setDurationMinutes(request.getDurationMinutes());
        if (request.getTotalMarks() != null)
            exam.setTotalMarks(request.getTotalMarks());
        if (request.getPassingMarks() != null)
            exam.setPassingMarks(request.getPassingMarks());
        if (request.getNegativeMarkingEnabled() != null)
            exam.setNegativeMarkingEnabled(request.getNegativeMarkingEnabled());
        if (request.getNegativeMarksPerQuestion() != null)
            exam.setNegativeMarksPerQuestion(request.getNegativeMarksPerQuestion());
        if (request.getActive() != null)
            exam.setActive(request.getActive());

        examRepository.save(exam);
    }

    @Override
    @Transactional
    public void patchQuestion(Long questionId, UpdateQuestionRequestDTO request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ExamPortalException("Question not found"));

        if (request.getQuestionText() != null)
            question.setQuestionText(request.getQuestionText());
        if (request.getQuestionType() != null)
            question.setQuestionType(request.getQuestionType());
        if (request.getMarks() != null)
            question.setMarks(request.getMarks());
        if (request.getCorrectAnswerKey() != null)
            question.setCorrectAnswerKey(request.getCorrectAnswerKey());

        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void patchOption(Long optionId, UpdateOptionRequestDTO request) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ExamPortalException("Option not found"));

        if (request.getOptionKey() != null)
            option.setOptionKey(request.getOptionKey());
        if (request.getOptionText() != null)
            option.setOptionText(request.getOptionText());

        optionRepository.save(option);
    }
}
