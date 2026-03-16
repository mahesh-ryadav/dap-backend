package com.defence.portal.examportal.dto;

import lombok.Data;

@Data
public class SubmitAnswerRequestDTO {
    private Long attemptId;
    private Long questionId;
    private String selectedOptionKey;
}
