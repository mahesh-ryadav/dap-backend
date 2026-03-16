package com.defence.portal.examportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "exam_results")
@Getter
@Setter
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id")
    private ExamAttempt attempt;

    @NotNull
    private Integer totalQuestions;

    @NotNull
    private Integer correctCount;

    private Integer incorrectCount;

    private Integer unattemptedCount;

    private Double finalScore;

    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;
}
