package com.defence.portal.studymaterials.controller;


import com.defence.portal.studymaterials.entity.Category;
import com.defence.portal.studymaterials.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {

    private final CategoryRepository repository;

    public AdminCategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return repository.save(category);
    }



    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        repository.deleteById(id);
        return "Category deleted successfully";
    }
}
