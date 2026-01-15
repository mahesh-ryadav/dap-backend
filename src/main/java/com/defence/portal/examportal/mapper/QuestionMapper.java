package com.defence.portal.examportal.mapper;

import com.defence.portal.examportal.dto.OptionDTO;
import com.defence.portal.examportal.dto.QuestionDTO;
import com.defence.portal.examportal.entity.Option;
import com.defence.portal.examportal.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO toDto(Question question);

    OptionDTO toDto(Option option);
}
