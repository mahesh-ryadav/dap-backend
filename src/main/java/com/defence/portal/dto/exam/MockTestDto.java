package com.defence.portal.dto.exam;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MockTestDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Exam type is required")
    private String examType; // BSF, SSC, NDA

    private String description;

    @NotNull(message = "Total marks is required")
    @Min(value = 1, message = "Total marks must be at least 1")
    private Integer totalMarks;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    private Boolean negativeMarking;

    private String status; // DRAFT, PUBLISHED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // For nested objects - can be null for create/update operations
    private List<SectionDto> sections;
}