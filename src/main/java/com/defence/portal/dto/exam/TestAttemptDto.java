package com.defence.portal.dto.exam;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TestAttemptDto {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status; // IN_PROGRESS, SUBMITTED

    private Integer totalScore;

    @NotNull(message = "Mock test ID is required")
    private Long mockTestId;

    // For nested objects
    private List<UserAnswerDto> answers;
}