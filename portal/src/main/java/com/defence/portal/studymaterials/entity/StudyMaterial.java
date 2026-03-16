package com.defence.portal.studymaterials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String originalName;
    private String fileType;
    private String filePath;
    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
