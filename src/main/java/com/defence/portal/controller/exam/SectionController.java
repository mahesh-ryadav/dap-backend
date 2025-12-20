package com.defence.portal.controller.exam;

import com.defence.portal.dto.exam.SectionDto;
import com.defence.portal.entity.exam.Section;
import com.defence.portal.service.exam.SectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam/sections")
@CrossOrigin(origins = "*")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    /* ===== CREATE SECTION ===== */
    @PostMapping
    public ResponseEntity<Section> createSection(@Valid @RequestBody SectionDto sectionDto) {
        Section saved = sectionService.createSection(sectionDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /* ===== UPDATE SECTION ===== */
    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable Long id,
                                                @Valid @RequestBody SectionDto sectionDto) {
        Section updated = sectionService.updateSection(id, sectionDto);
        return ResponseEntity.ok(updated);
    }

    /* ===== GET SECTION BY ID ===== */
    @GetMapping("/{id}")
    public ResponseEntity<SectionDto> getSectionById(@PathVariable Long id) {
        SectionDto section = sectionService.getSectionById(id);
        return ResponseEntity.ok(section);
    }

    /* ===== GET ALL SECTIONS ===== */
    @GetMapping
    public ResponseEntity<List<SectionDto>> getAllSections() {
        List<SectionDto> sections = sectionService.getAllSections();
        return ResponseEntity.ok(sections);
    }

    /* ===== GET SECTIONS BY MOCK TEST ID ===== */
    @GetMapping("/mock-test/{mockTestId}")
    public ResponseEntity<List<SectionDto>> getSectionsByMockTestId(@PathVariable Long mockTestId) {
        List<SectionDto> sections = sectionService.getSectionsByMockTestId(mockTestId);
        return ResponseEntity.ok(sections);
    }

    /* ===== DELETE SECTION ===== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.ok("Section deleted successfully");
    }
}