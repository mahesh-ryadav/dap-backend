package com.defence.portal.examportal.mapper;

import com.defence.portal.examportal.dto.ExamDTO;
import com.defence.portal.examportal.entity.Exam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    ExamDTO toDto(Exam exam);

    Exam toEntity(ExamDTO dto);
}
