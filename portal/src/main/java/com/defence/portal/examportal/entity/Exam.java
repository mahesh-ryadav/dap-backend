package com.defence.portal.examportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "exams")
@Getter
@Setter
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    @NotNull(message = "Total marks is required")
    @Min(value = 1, message = "Total marks must be generally positive")
    private Integer totalMarks;

    @Min(value = 0, message = "Passing marks must be non-negative")
    private Integer passingMarks;

    private boolean negativeMarkingEnabled;

    @PositiveOrZero(message = "Negative marks cannot be negative number")
    private Double negativeMarksPerQuestion;

    private boolean active;

    private LocalDateTime createdAt;

    // Rel: One Exam -> Many Questions
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;

    // Rel: One Exam -> Many ExamAttempts
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExamAttempt> attempts;
}
