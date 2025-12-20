package com.defence.portal.service.exam;

import com.defence.portal.dto.exam.QuestionDto;
import com.defence.portal.entity.exam.Question;
import java.util.List;

public interface QuestionService {

    // Create a new question
    Question createQuestion(QuestionDto questionDto);

    // Update existing question
    Question updateQuestion(Long id, QuestionDto questionDto);

    // Get question by ID
    QuestionDto getQuestionById(Long id);

    // Get all questions
    List<QuestionDto> getAllQuestions();

    // Get questions by section
    List<QuestionDto> getQuestionsBySection(Long sectionId);

    // Delete question
    void deleteQuestion(Long id);
}