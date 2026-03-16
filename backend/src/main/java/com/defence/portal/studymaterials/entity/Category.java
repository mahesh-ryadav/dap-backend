package com.defence.portal.studymaterials.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // NDA, CDS, etc.
    private String description;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StudyMaterial> materials;

}