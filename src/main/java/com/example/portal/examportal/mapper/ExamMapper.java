package com.example.portal.examportal.mapper;

import com.example.portal.examportal.dto.ExamDTO;
import com.example.portal.examportal.entity.Exam;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {

    public ExamDTO toDto(Exam exam) {
        if (exam == null)
            return null;
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setDurationMinutes(exam.getDurationMinutes());
        dto.setTotalMarks(exam.getTotalMarks());
        dto.setPassingMarks(exam.getPassingMarks());
        dto.setNegativeMarkingEnabled(exam.isNegativeMarkingEnabled());
        dto.setActive(exam.isActive());
        return dto;
    }

    public Exam toEntity(ExamDTO dto) {
        if (dto == null)
            return null;
        Exam exam = new Exam();
        if (dto.getId() != null) {
            exam.setId(dto.getId());
        }
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setDurationMinutes(dto.getDurationMinutes());
        exam.setTotalMarks(dto.getTotalMarks());
        exam.setPassingMarks(dto.getPassingMarks());
        exam.setNegativeMarkingEnabled(dto.isNegativeMarkingEnabled());
        exam.setActive(dto.isActive());
        return exam;
    }
}
