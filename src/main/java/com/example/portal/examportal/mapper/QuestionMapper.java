package com.example.portal.examportal.mapper;

import com.example.portal.examportal.dto.OptionDTO;
import com.example.portal.examportal.dto.QuestionDTO;
import com.example.portal.examportal.entity.Option;
import com.example.portal.examportal.entity.Question;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public QuestionDTO toDto(Question question, List<Option> options) {
        if (question == null)
            return null;

        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setQuestionType(question.getQuestionType());
        dto.setMarks(question.getMarks());

        // Map options if present
        if (options != null && !options.isEmpty()) {
            List<OptionDTO> optionDTOs = options.stream()
                    .map(this::toOptionDto)
                    .collect(Collectors.toList());
            dto.setOptions(optionDTOs);
        } else {
            dto.setOptions(Collections.emptyList());
        }

        return dto;
    }

    private OptionDTO toOptionDto(Option option) {
        if (option == null)
            return null;
        OptionDTO dto = new OptionDTO();
        dto.setOptionKey(option.getOptionKey());
        dto.setOptionText(option.getOptionText());
        return dto;
    }
}
