package com.defence.portal.controller.exam;

import com.defence.portal.dto.exam.MockTestDto;
import com.defence.portal.entity.exam.MockTest;
import com.defence.portal.service.exam.MockTestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam/mock-tests")
@CrossOrigin(origins = "*")
public class MockTestController {

    private final MockTestService mockTestService;

    public MockTestController(MockTestService mockTestService) {
        this.mockTestService = mockTestService;
    }

    /* ===== CREATE MOCK TEST ===== */
    @PostMapping
    public ResponseEntity<MockTest> createMockTest(@Valid @RequestBody MockTestDto mockTestDto) {
        MockTest saved = mockTestService.createMockTest(mockTestDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /* ===== UPDATE MOCK TEST ===== */
    @PutMapping("/{id}")
    public ResponseEntity<MockTest> updateMockTest(@PathVariable Long id,
                                                  @Valid @RequestBody MockTestDto mockTestDto) {
        MockTest updated = mockTestService.updateMockTest(id, mockTestDto);
        return ResponseEntity.ok(updated);
    }

    /* ===== GET MOCK TEST BY ID ===== */
    @GetMapping("/{id}")
    public ResponseEntity<MockTestDto> getMockTestById(@PathVariable Long id) {
        MockTestDto mockTest = mockTestService.getMockTestById(id);
        return ResponseEntity.ok(mockTest);
    }

    /* ===== GET ALL MOCK TESTS ===== */
    @GetMapping
    public ResponseEntity<List<MockTestDto>> getAllMockTests() {
        List<MockTestDto> mockTests = mockTestService.getAllMockTests();
        return ResponseEntity.ok(mockTests);
    }

    /* ===== GET PUBLISHED MOCK TESTS ===== */
    @GetMapping("/published")
    public ResponseEntity<List<MockTestDto>> getPublishedMockTests() {
        List<MockTestDto> mockTests = mockTestService.getPublishedMockTests();
        return ResponseEntity.ok(mockTests);
    }

    /* ===== GET MOCK TESTS BY EXAM TYPE ===== */
    @GetMapping("/exam-type/{examType}")
    public ResponseEntity<List<MockTestDto>> getMockTestsByExamType(@PathVariable String examType) {
        List<MockTestDto> mockTests = mockTestService.getMockTestsByExamType(examType);
        return ResponseEntity.ok(mockTests);
    }

    /* ===== PUBLISH MOCK TEST ===== */
    @PutMapping("/{id}/publish")
    public ResponseEntity<MockTest> publishMockTest(@PathVariable Long id) {
        MockTest published = mockTestService.publishMockTest(id);
        return ResponseEntity.ok(published);
    }

    /* ===== DELETE MOCK TEST ===== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMockTest(@PathVariable Long id) {
        mockTestService.deleteMockTest(id);
        return ResponseEntity.ok("Mock test deleted successfully");
    }
}