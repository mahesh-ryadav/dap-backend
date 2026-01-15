package com.defence.portal.examportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "exam_attempts")
@Getter
@Setter
public class ExamAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AttemptStatus status;

    private Double score;

    // Rel: One ExamAttempt -> Many AttemptAnswers
    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttemptAnswer> answers;

    // Rel: One ExamAttempt -> One ExamResult
    @OneToOne(mappedBy = "attempt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ExamResult result;
}
