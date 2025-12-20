package com.defence.portal.entity.exam;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalQuestions;
    private Integer correctCount;
    private Integer wrongCount;
    private Integer unattemptedCount;
    private Integer finalScore;
    private Double percentage;

    @OneToOne
    @JoinColumn(name = "attempt_id")
    private TestAttempt testAttempt;
}
