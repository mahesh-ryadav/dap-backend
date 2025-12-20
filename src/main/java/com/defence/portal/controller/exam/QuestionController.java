package com.defence.portal.controller.exam;

import com.defence.portal.dto.exam.QuestionDto;
import com.defence.portal.entity.exam.Question;
import com.defence.portal.service.exam.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /* ===== CREATE QUESTION ===== */
    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionDto questionDto) {
        Question saved = questionService.createQuestion(questionDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /* ===== UPDATE QUESTION ===== */
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,
                                                  @Valid @RequestBody QuestionDto questionDto) {
        Question updated = questionService.updateQuestion(id, questionDto);
        return ResponseEntity.ok(updated);
    }

    /* ===== GET QUESTION BY ID ===== */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
        QuestionDto question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    /* ===== GET ALL QUESTIONS ===== */
    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<QuestionDto> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    /* ===== GET QUESTIONS BY SECTION ===== */
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsBySection(@PathVariable Long sectionId) {
        List<QuestionDto> questions = questionService.getQuestionsBySection(sectionId);
        return ResponseEntity.ok(questions);
    }

    /* ===== DELETE QUESTION ===== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok("Question deleted successfully");
    }
}