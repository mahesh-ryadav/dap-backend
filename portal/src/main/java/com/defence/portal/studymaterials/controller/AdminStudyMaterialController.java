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
@RequestMapping("/api/admin/study-material")
public class AdminStudyMaterialController {

    private final StudyMaterialService service;

    public AdminStudyMaterialController(
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





    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id)
            throws IOException {

        service.deleteFile(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
