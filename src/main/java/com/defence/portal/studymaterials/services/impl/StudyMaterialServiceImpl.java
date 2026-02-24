package com.defence.portal.studymaterials.services.impl;

import com.defence.portal.studymaterials.entity.Category;
import com.defence.portal.studymaterials.entity.StudyMaterial;
import com.defence.portal.studymaterials.repository.CategoryRepository;
import com.defence.portal.studymaterials.repository.StudyMaterialRepository;
import com.defence.portal.studymaterials.services.StudyMaterialService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StudyMaterialServiceImpl
        implements StudyMaterialService {

    private final StudyMaterialRepository repository;
    private final CategoryRepository categoryRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public StudyMaterialServiceImpl(
            StudyMaterialRepository repository,
            CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public StudyMaterial uploadFile(
            MultipartFile file,
            Long categoryId) throws IOException {

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (!Objects.requireNonNull(file.getContentType())
                .startsWith("application/pdf")) {
            throw new RuntimeException("Only PDF allowed");
        }

        // Create category folder
        Path uploadPath = Paths.get(uploadDir, category.getName());
        Files.createDirectories(uploadPath);

        String storedName = UUID.randomUUID()
                + "_" + file.getOriginalFilename();

        Path filePath = uploadPath.resolve(storedName);

        Files.copy(file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING);

        StudyMaterial material = new StudyMaterial();
        material.setFileName(storedName);
        material.setOriginalName(file.getOriginalFilename());
        material.setFileType(file.getContentType());
        material.setFilePath(filePath.toString());
        material.setFileSize(file.getSize());
        material.setCategory(category);

        return repository.save(material);
    }

    @Override
    public List<StudyMaterial> getAllFiles() {
        return repository.findAll();
    }

    @Override
    public List<StudyMaterial> getFilesByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public StudyMaterial getFileById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));
    }

    @Override
    public UrlResource downloadFile(Long id)
            throws IOException {

        StudyMaterial material = getFileById(id);

        Path path = Paths.get(material.getFilePath());

        if (!Files.exists(path)) {
            throw new RuntimeException("File not found on server");
        }

        return new UrlResource(path.toUri());
    }

    @Override
    public void deleteFile(Long id)
            throws IOException {

        StudyMaterial material = getFileById(id);

        Path path = Paths.get(material.getFilePath());
        Files.deleteIfExists(path);

        repository.deleteById(id);
    }
}