package com.example.portal.examportal.dto;

import lombok.Data;

@Data
public class SubmitAnswerRequestDTO {
    private Long attemptId;
    private Long questionId;
    private String selectedOptionKey;
}
