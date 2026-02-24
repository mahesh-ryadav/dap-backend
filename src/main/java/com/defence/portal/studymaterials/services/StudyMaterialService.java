package com.defence.portal.studymaterials.services;

import com.defence.portal.studymaterials.entity.StudyMaterial;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudyMaterialService {

    StudyMaterial uploadFile(
            MultipartFile file,
            Long categoryId) throws IOException;

    List<StudyMaterial> getAllFiles();

    List<StudyMaterial> getFilesByCategory(Long categoryId);

    StudyMaterial getFileById(Long id);

    UrlResource downloadFile(Long id) throws IOException;

    void deleteFile(Long id) throws IOException;
}