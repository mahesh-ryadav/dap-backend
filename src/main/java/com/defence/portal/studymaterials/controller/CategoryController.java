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

    // ✅ Create Category
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return repository.save(category);
    }

    // ✅ Get All Categories
    @GetMapping
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    // ✅ Get Category By Id
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
    }

    // ✅ Delete Category
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        repository.deleteById(id);
        return "Category deleted successfully";
    }
}
