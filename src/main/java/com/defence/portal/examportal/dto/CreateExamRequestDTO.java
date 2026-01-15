package com.defence.portal.examportal.dto;

import lombok.Data;

@Data
public class CreateExamRequestDTO {
    private String title;
    private String description;
    private Integer durationMinutes;
    private Integer totalMarks;
    private Integer passingMarks;
    private boolean negativeMarkingEnabled;
    private Double negativeMarksPerQuestion;
}
