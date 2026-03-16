package com.defence.portal.examportal.dto;

import lombok.Data;

@Data
public class ExamDTO {
    private Long id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Integer totalMarks;
    private Integer passingMarks;
    private boolean negativeMarkingEnabled;
    private boolean active;
}
