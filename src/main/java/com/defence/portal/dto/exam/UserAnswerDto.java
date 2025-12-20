package com.defence.portal.dto.exam;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserAnswerDto {

    private Long id;

    private Long selectedOptionId;
    private Boolean isCorrect;
    private Integer marksObtained;

    private Long attemptId;

    @NotNull(message = "Question ID is required")
    private Long questionId;
}