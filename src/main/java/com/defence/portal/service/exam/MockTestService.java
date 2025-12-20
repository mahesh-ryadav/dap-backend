package com.defence.portal.service.exam;

import com.defence.portal.dto.exam.MockTestDto;
import com.defence.portal.entity.exam.MockTest;
import java.util.List;

public interface MockTestService {

    // Create a new mock test
    MockTest createMockTest(MockTestDto mockTestDto);

    // Update existing mock test
    MockTest updateMockTest(Long id, MockTestDto mockTestDto);

    // Get mock test by ID
    MockTestDto getMockTestById(Long id);

    // Get all mock tests
    List<MockTestDto> getAllMockTests();

    // Get published mock tests
    List<MockTestDto> getPublishedMockTests();

    // Get mock tests by exam type
    List<MockTestDto> getMockTestsByExamType(String examType);

    // Delete mock test
    void deleteMockTest(Long id);

    // Publish mock test
    MockTest publishMockTest(Long id);
}