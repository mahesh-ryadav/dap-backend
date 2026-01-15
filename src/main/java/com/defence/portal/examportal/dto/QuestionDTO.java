package com.defence.portal.examportal.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String questionText;
    private String questionType;
    private Integer marks;
    private List<OptionDTO> options;
}
