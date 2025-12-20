package com.defence.portal.entity.exam;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_answers")
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long selectedOptionId;
    private Boolean isCorrect;
    private Integer marksObtained;

    @ManyToOne
    @JoinColumn(name = "attempt_id")
    private TestAttempt testAttempt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
