package com.defence.portal.dto.exam;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResultDto {

    private Long id;

    private Integer totalQuestions;
    private Integer correctCount;
    private Integer wrongCount;
    private Integer unattemptedCount;
    private Integer finalScore;
    private Double percentage;

    @NotNull(message = "Attempt ID is required")
    private Long attemptId;
}