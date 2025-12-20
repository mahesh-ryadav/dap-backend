package com.defence.portal.controller.exam;

import com.defence.portal.dto.exam.TestAttemptDto;
import com.defence.portal.entity.exam.TestAttempt;
import com.defence.portal.service.exam.TestAttemptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam/attempts")
@CrossOrigin(origins = "*")
public class TestAttemptController {

    private final TestAttemptService testAttemptService;

    public TestAttemptController(TestAttemptService testAttemptService) {
        this.testAttemptService = testAttemptService;
    }

    /* ===== START TEST ATTEMPT ===== */
    @PostMapping("/start")
    public ResponseEntity<TestAttempt> startTestAttempt(@Valid @RequestBody TestAttemptDto testAttemptDto) {
        TestAttempt started = testAttemptService.startTestAttempt(testAttemptDto);
        return new ResponseEntity<>(started, HttpStatus.CREATED);
    }

    /* ===== SUBMIT TEST ATTEMPT ===== */
    @PutMapping("/{attemptId}/submit")
    public ResponseEntity<TestAttempt> submitTestAttempt(@PathVariable Long attemptId) {
        TestAttempt submitted = testAttemptService.submitTestAttempt(attemptId);
        return ResponseEntity.ok(submitted);
    }

    /* ===== GET TEST ATTEMPT BY ID ===== */
    @GetMapping("/{id}")
    public ResponseEntity<TestAttemptDto> getTestAttemptById(@PathVariable Long id) {
        TestAttemptDto attempt = testAttemptService.getTestAttemptById(id);
        return ResponseEntity.ok(attempt);
    }

    /* ===== GET ATTEMPTS BY USER ===== */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TestAttemptDto>> getAttemptsByUser(@PathVariable Long userId) {
        List<TestAttemptDto> attempts = testAttemptService.getAttemptsByUser(userId);
        return ResponseEntity.ok(attempts);
    }

    /* ===== GET ATTEMPTS BY MOCK TEST ===== */
    @GetMapping("/mock-test/{mockTestId}")
    public ResponseEntity<List<TestAttemptDto>> getAttemptsByMockTest(@PathVariable Long mockTestId) {
        List<TestAttemptDto> attempts = testAttemptService.getAttemptsByMockTest(mockTestId);
        return ResponseEntity.ok(attempts);
    }

    /* ===== GET ALL TEST ATTEMPTS ===== */
    @GetMapping
    public ResponseEntity<List<TestAttemptDto>> getAllTestAttempts() {
        List<TestAttemptDto> attempts = testAttemptService.getAllTestAttempts();
        return ResponseEntity.ok(attempts);
    }

    /* ===== DELETE TEST ATTEMPT ===== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestAttempt(@PathVariable Long id) {
        testAttemptService.deleteTestAttempt(id);
        return ResponseEntity.ok("Test attempt deleted successfully");
    }
}