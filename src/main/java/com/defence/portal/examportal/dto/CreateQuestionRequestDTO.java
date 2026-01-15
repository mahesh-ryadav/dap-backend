package com.defence.portal.examportal.dto;

import lombok.Data;

@Data
public class CreateQuestionRequestDTO {
    private String questionText;
    private String questionType;
    private Integer marks;
    private String correctAnswerKey;
}
