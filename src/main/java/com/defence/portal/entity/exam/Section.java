package com.defence.portal.entity.exam;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;               // Maths, GK
    private Integer sectionMarks;
    private Integer totalQuestions;

    @ManyToOne
    @JoinColumn(name = "mock_test_id")
    private MockTest mockTest;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<Question> questions;
}
