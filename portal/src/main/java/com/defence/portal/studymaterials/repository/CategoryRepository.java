package com.defence.portal.studymaterials.repository;

import com.defence.portal.studymaterials.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {
}
