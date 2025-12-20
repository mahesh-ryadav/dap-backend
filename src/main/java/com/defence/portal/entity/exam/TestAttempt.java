package com.defence.portal.entity.exam;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_attempts")
public class TestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;               // FK to User table
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status;             // IN_PROGRESS, SUBMITTED
    private Integer totalScore;

    @ManyToOne
    @JoinColumn(name = "mock_test_id")
    private MockTest mockTest;

    @OneToMany(mappedBy = "testAttempt", cascade = CascadeType.ALL)
    private List<UserAnswer> answers;
}
