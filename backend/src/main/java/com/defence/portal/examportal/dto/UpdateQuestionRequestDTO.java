package com.defence.portal.examportal.dto;

import lombok.Data;

@Data
public class UpdateQuestionRequestDTO {
    private String questionText;
    private String questionType;
    private Integer marks;
    private String correctAnswerKey;
}
