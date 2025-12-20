package com.defence.portal.dto.exam;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class QuestionDto {

    private Long id;

    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotBlank(message = "Question type is required")
    private String questionType; // MCQ, NUMERIC

    @NotNull(message = "Marks is required")
    @Min(value = 1, message = "Marks must be at least 1")
    private Integer marks;

    private Integer negativeMarks;

    @NotBlank(message = "Difficulty level is required")
    private String difficultyLevel; // EASY, MEDIUM, HARD

    private Long sectionId;

    // For nested objects
    private List<OptionDto> options;
}