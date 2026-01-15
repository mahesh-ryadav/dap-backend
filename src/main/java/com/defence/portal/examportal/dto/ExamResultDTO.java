package com.defence.portal.examportal.dto;

import lombok.Data;

@Data
public class ExamResultDTO {
    private Integer totalQuestions;
    private Integer correctCount;
    private Integer incorrectCount;
    private Integer unattemptedCount;
    private Double finalScore;
    private String resultStatus;
}
