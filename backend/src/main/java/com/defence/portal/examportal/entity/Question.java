package com.defence.portal.examportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Question text is required")
    private String questionText;

    @NotBlank(message = "Question type is required")
    private String questionType; // e.g., MCQ, SUBJECTIVE

    @NotNull(message = "Marks are required")
    @Min(value = 1, message = "Marks must be at least 1")
    private Integer marks;

    @NotBlank(message = "Correct answer key is required")
    private String correctAnswerKey;

    private LocalDateTime createdAt;

    // Rel: One Question -> Many Options
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Option> options;
}
