package com.defence.portal.examportal.mapper;

import com.defence.portal.examportal.dto.ExamResultDTO;
import com.defence.portal.examportal.entity.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {

    @Mapping(target = "resultStatus", expression = "java(result.getResultStatus() != null ? result.getResultStatus().name() : null)")
    ExamResultDTO toDto(ExamResult result);
}
