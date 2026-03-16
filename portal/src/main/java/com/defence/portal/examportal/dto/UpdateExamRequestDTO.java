package com.defence.portal.examportal.dto;

import lombok.Data;

@Data
public class UpdateExamRequestDTO {
    private String title;
    private String description;
    private Integer durationMinutes;
    private Integer totalMarks;
    private Integer passingMarks;
    private Boolean negativeMarkingEnabled;
    private Double negativeMarksPerQuestion;
    private Boolean active;
}
