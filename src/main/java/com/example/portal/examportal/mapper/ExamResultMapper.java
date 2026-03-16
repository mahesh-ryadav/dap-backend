package com.example.portal.examportal.mapper;

import com.example.portal.examportal.dto.ExamResultDTO;
import com.example.portal.examportal.entity.ExamResult;
import org.springframework.stereotype.Component;

@Component
public class ExamResultMapper {

    public ExamResultDTO toDto(ExamResult result) {
        if (result == null)
            return null;

        ExamResultDTO dto = new ExamResultDTO();
        dto.setTotalQuestions(result.getTotalQuestions());
        dto.setCorrectCount(result.getCorrectCount());
        dto.setIncorrectCount(result.getIncorrectCount());
        dto.setUnattemptedCount(result.getUnattemptedCount());
        dto.setFinalScore(result.getFinalScore());

        if (result.getResultStatus() != null) {
            dto.setResultStatus(result.getResultStatus().name());
        }

        return dto;
    }
}
