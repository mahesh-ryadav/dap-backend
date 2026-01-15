package com.defence.portal.examportal.service.impl;

import com.defence.portal.examportal.dto.ExamDTO;
import com.defence.portal.examportal.dto.ExamResultDTO;
import com.defence.portal.examportal.dto.QuestionDTO;
import com.defence.portal.examportal.entity.*;
import com.defence.portal.examportal.exception.ExamPortalException;
import com.defence.portal.examportal.mapper.ExamMapper;
import com.defence.portal.examportal.mapper.ExamResultMapper;
import com.defence.portal.examportal.mapper.QuestionMapper;
import com.defence.portal.examportal.repository.*;
import com.defence.portal.examportal.service.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final AttemptAnswerRepository attemptAnswerRepository;
    private final ExamResultRepository examResultRepository;

    private final ExamMapper examMapper;
    private final QuestionMapper questionMapper;
    private final ExamResultMapper examResultMapper;

    public ExamServiceImpl(ExamRepository examRepository,
            QuestionRepository questionRepository,
            ExamAttemptRepository examAttemptRepository,
            AttemptAnswerRepository attemptAnswerRepository,
            ExamResultRepository examResultRepository,
            ExamMapper examMapper,
            QuestionMapper questionMapper,
            ExamResultMapper examResultMapper) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.examAttemptRepository = examAttemptRepository;
        this.attemptAnswerRepository = attemptAnswerRepository;
        this.examResultRepository = examResultRepository;
        this.examMapper = examMapper;
        this.questionMapper = questionMapper;
        this.examResultMapper = examResultMapper;
    }

    @Override
    public List<ExamDTO> getActiveExams() {
        return examRepository.findByActiveTrue().stream()
                .map(examMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExamDTO getExamDetails(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamPortalException("Exam not found with id: " + examId));
        return examMapper.toDto(exam);
    }

    @Override
    @Transactional
    public Long startExam(Long examId) {
        com.defence.portal.auth.service.UserDetailsImpl userDetails = (com.defence.portal.auth.service.UserDetailsImpl) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        // Check for existing active attempt
        Optional<ExamAttempt> activeAttempt = examAttemptRepository.findByUserIdAndExamIdAndStatus(
                userId, examId, AttemptStatus.STARTED);

        if (activeAttempt.isPresent()) {
            return activeAttempt.get().getId();
        }

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamPortalException("Exam not found"));

        if (!exam.isActive()) {
            throw new ExamPortalException("Exam is not active");
        }

        ExamAttempt attempt = new ExamAttempt();
        attempt.setExam(exam);
        attempt.setUserId(userId);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.STARTED);

        ExamAttempt savedAttempt = examAttemptRepository.save(attempt);
        return savedAttempt.getId();
    }

    @Override
    public List<QuestionDTO> getQuestionsForAttempt(Long attemptId) {
        ExamAttempt attempt = examAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ExamPortalException("Attempt not found"));

        if (attempt.getStatus() != AttemptStatus.STARTED) {
            throw new ExamPortalException("Exam attempt is not active");
        }

        List<Question> questions = questionRepository.findByExamId(attempt.getExam().getId());
        return questions.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void submitAnswer(Long attemptId, Long questionId, String selectedOptionKey) {
        ExamAttempt attempt = examAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ExamPortalException("Attempt not found"));

        if (attempt.getStatus() != AttemptStatus.STARTED) {
            throw new ExamPortalException("Cannot submit answer. Exam is not active.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ExamPortalException("Question not found"));

        // Ensure question belongs to the exam of this attempt
        if (!question.getExam().getId().equals(attempt.getExam().getId())) {
            throw new ExamPortalException("Question does not belong to this exam");
        }

        Optional<AttemptAnswer> existingAnswer = attemptAnswerRepository.findByAttemptIdAndQuestionId(attemptId,
                questionId);

        AttemptAnswer answer;
        if (existingAnswer.isPresent()) {
            answer = existingAnswer.get();
        } else {
            answer = new AttemptAnswer();
            answer.setAttempt(attempt);
            answer.setQuestion(question);
        }

        answer.setSelectedOptionKey(selectedOptionKey);
        // Determine correctness immediately or during result calculation?
        // Requirement says "Calculates score and result during submission", but knowing
        // correctness is useful.
        // Let's store correctness now for simpler calculation later.
        answer.setCorrect(selectedOptionKey.equalsIgnoreCase(question.getCorrectAnswerKey()));

        attemptAnswerRepository.save(answer);
    }

    @Override
    @Transactional
    public ExamResultDTO submitExam(Long attemptId) {
        ExamAttempt attempt = examAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ExamPortalException("Attempt not found"));

        if (attempt.getStatus() != AttemptStatus.STARTED) {
            // If already submitted, return the result if it exists
            if (attempt.getStatus() == AttemptStatus.SUBMITTED) {
                return getExamResult(attemptId);
            }
            throw new ExamPortalException("Invalid attempt status for submission");
        }

        Exam exam = attempt.getExam();
        List<AttemptAnswer> answers = attemptAnswerRepository.findByAttemptId(attemptId);
        List<Question> questions = questionRepository.findByExamId(exam.getId());

        int totalQuestions = questions.size();
        int correctCount = 0;
        int incorrectCount = 0;
        int unattemptedCount = 0;

        // Calculate score
        // Only count answers that match questions in this exam (integrity check)
        for (Question question : questions) {
            Optional<AttemptAnswer> answerOpt = answers.stream()
                    .filter(a -> a.getQuestion().getId().equals(question.getId()))
                    .findFirst();

            if (answerOpt.isPresent()) {
                AttemptAnswer answer = answerOpt.get();
                if (answer.isCorrect()) {
                    correctCount++;
                } else {
                    incorrectCount++;
                }
            } else {
                unattemptedCount++;
            }
        }

        // Calculate total score based on individual question marks
        double totalExamScore = 0.0;
        for (Question question : questions) {
            Optional<AttemptAnswer> answerOpt = answers.stream()
                    .filter(a -> a.getQuestion().getId().equals(question.getId()))
                    .findFirst();

            if (answerOpt.isPresent()) {
                AttemptAnswer answer = answerOpt.get();
                if (answer.isCorrect()) {
                    totalExamScore += question.getMarks();
                } else {
                    if (exam.isNegativeMarkingEnabled() && exam.getNegativeMarksPerQuestion() != null) {
                        totalExamScore -= exam.getNegativeMarksPerQuestion();
                    }
                }
            }
        }
        // Ensure score doesn't go below zero if desired, typically exams can have
        // negative scores or floor at 0.
        // Let's assume floor at 0 for safety unless specified.
        if (totalExamScore < 0)
            totalExamScore = 0;

        ExamResult result = new ExamResult();
        result.setAttempt(attempt);
        result.setTotalQuestions(totalQuestions);
        result.setCorrectCount(correctCount);
        result.setIncorrectCount(incorrectCount);
        result.setUnattemptedCount(unattemptedCount);
        result.setFinalScore(totalExamScore);

        // Determine Pass/Fail
        Integer passingMarks = exam.getPassingMarks();
        if (passingMarks != null && totalExamScore >= passingMarks) {
            result.setResultStatus(ResultStatus.PASS);
        } else if (passingMarks != null) {
            result.setResultStatus(ResultStatus.FAIL);
        } else {
            // Fallback if passing marks not set, defaulted to PASS as per original mock
            // behavior
            // or maybe check if score > 0 ? For now, let's keep it PASS to avoid blocking
            // unless 0.
            // Actually, safer to default to PASS for mocks if no criteria provided.
            result.setResultStatus(ResultStatus.PASS);
        }

        examResultRepository.save(result);

        attempt.setStatus(AttemptStatus.SUBMITTED);
        attempt.setEndTime(LocalDateTime.now());
        attempt.setScore(totalExamScore);
        attempt.setResult(result);
        examAttemptRepository.save(attempt);

        return examResultMapper.toDto(result);
    }

    @Override
    public ExamResultDTO getExamResult(Long attemptId) {
        ExamResult result = examResultRepository.findByAttemptId(attemptId)
                .orElseThrow(() -> new ExamPortalException("Result not found for attempt: " + attemptId));
        return examResultMapper.toDto(result);
    }
}
