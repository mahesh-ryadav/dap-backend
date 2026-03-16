package com.defence.portal.studymaterials.controller;


import com.defence.portal.studymaterials.entity.Category;
import com.defence.portal.studymaterials.repository.CategoryRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
    }


}
