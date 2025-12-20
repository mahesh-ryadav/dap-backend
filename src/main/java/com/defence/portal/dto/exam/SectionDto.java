package com.defence.portal.dto.exam;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class SectionDto {

    private Long id;

    @NotBlank(message = "Section name is required")
    private String name;

    @NotNull(message = "Section marks is required")
    @Min(value = 1, message = "Section marks must be at least 1")
    private Integer sectionMarks;

    @NotNull(message = "Total questions is required")
    @Min(value = 1, message = "Total questions must be at least 1")
    private Integer totalQuestions;

    private Long mockTestId;

    // For nested objects
    private List<QuestionDto> questions;
}