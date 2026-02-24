package com.defence.portal.studymaterials.controller;


import com.defence.portal.studymaterials.entity.StudyMaterial;
import com.defence.portal.studymaterials.services.StudyMaterialService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/study-material")
public class StudyMaterialController {

    private final StudyMaterialService service;

    public StudyMaterialController(
            StudyMaterialService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryId") Long categoryId)
            throws IOException {

        return ResponseEntity.ok(
                service.uploadFile(file, categoryId));
    }

    @GetMapping
    public ResponseEntity<List<StudyMaterial>> getAll() {
        return ResponseEntity.ok(service.getAllFiles());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<StudyMaterial>>
    getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(
                service.getFilesByCategory(categoryId));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(
            @PathVariable Long id)
            throws IOException {

        Resource resource = service.downloadFile(id);
        StudyMaterial material = service.getFileById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        material.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + material.getOriginalName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id)
            throws IOException {

        service.deleteFile(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
