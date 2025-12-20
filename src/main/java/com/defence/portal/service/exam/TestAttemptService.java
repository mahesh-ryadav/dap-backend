package com.defence.portal.service.exam;

import com.defence.portal.dto.exam.TestAttemptDto;
import com.defence.portal.entity.exam.TestAttempt;
import java.util.List;

public interface TestAttemptService {

    // Start a new test attempt
    TestAttempt startTestAttempt(TestAttemptDto testAttemptDto);

    // Submit test attempt
    TestAttempt submitTestAttempt(Long attemptId);

    // Get test attempt by ID
    TestAttemptDto getTestAttemptById(Long id);

    // Get all attempts by user
    List<TestAttemptDto> getAttemptsByUser(Long userId);

    // Get attempts by mock test
    List<TestAttemptDto> getAttemptsByMockTest(Long mockTestId);

    // Get all test attempts
    List<TestAttemptDto> getAllTestAttempts();

    // Delete test attempt
    void deleteTestAttempt(Long id);
}