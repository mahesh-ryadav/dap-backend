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
@Table(name = "mock_tests")
public class MockTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String examType;          // BSF, SSC, NDA
    private String description;

    private Integer totalMarks;
    private Integer durationMinutes;

    private Boolean negativeMarking;
    private String status;            // DRAFT, PUBLISHED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "mockTest", cascade = CascadeType.ALL)
    private List<Section> sections;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
