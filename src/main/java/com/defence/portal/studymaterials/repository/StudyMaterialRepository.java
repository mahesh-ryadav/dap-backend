package com.defence.portal.studymaterials.repository;

import com.defence.portal.studymaterials.entity.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudyMaterialRepository
        extends JpaRepository<StudyMaterial, Long> {

    List<StudyMaterial> findByCategoryId(Long categoryId);
}
