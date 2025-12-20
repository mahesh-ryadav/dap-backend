package com.defence.portal.dto.exam;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OptionDto {

    private Long id;

    @NotBlank(message = "Option text is required")
    private String optionText;

    @NotNull(message = "Correct flag is required")
    private Boolean isCorrect;

    private Long questionId;
}