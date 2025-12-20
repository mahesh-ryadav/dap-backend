package com.defence.portal.service.exam.impl;

import com.defence.portal.dto.exam.TestAttemptDto;
import com.defence.portal.entity.exam.MockTest;
import com.defence.portal.entity.exam.TestAttempt;
import com.defence.portal.exception.ResourceNotFoundException;
import com.defence.portal.repository.exam.MockTestRepository;
import com.defence.portal.repository.exam.TestAttemptRepository;
import com.defence.portal.service.exam.TestAttemptService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TestAttemptServiceImpl implements TestAttemptService {

    private final TestAttemptRepository testAttemptRepository;
    private final MockTestRepository mockTestRepository;
    private final ModelMapper modelMapper;

    public TestAttemptServiceImpl(TestAttemptRepository testAttemptRepository,
                                 MockTestRepository mockTestRepository,
                                 ModelMapper modelMapper) {
        this.testAttemptRepository = testAttemptRepository;
        this.mockTestRepository = mockTestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TestAttempt startTestAttempt(TestAttemptDto testAttemptDto) {
        TestAttempt testAttempt = modelMapper.map(testAttemptDto, TestAttempt.class);

        // Set mock test
        MockTest mockTest = mockTestRepository.findById(testAttemptDto.getMockTestId())
                .orElseThrow(() -> new ResourceNotFoundException("MockTest not found with id: " + testAttemptDto.getMockTestId()));
        testAttempt.setMockTest(mockTest);

        // Set start time and status
        testAttempt.setStartTime(LocalDateTime.now());
        testAttempt.setStatus("IN_PROGRESS");

        return testAttemptRepository.save(testAttempt);
    }

    @Override
    public TestAttempt submitTestAttempt(Long attemptId) {
        TestAttempt testAttempt = testAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt not found with id: " + attemptId));

        // Set end time and status
        testAttempt.setEndTime(LocalDateTime.now());
        testAttempt.setStatus("SUBMITTED");

        // Calculate total score from user answers
        // This would need to be implemented based on the answers
        // For now, we'll set it to 0
        testAttempt.setTotalScore(0);

        return testAttemptRepository.save(testAttempt);
    }

    @Override
    @Transactional(readOnly = true)
    public TestAttemptDto getTestAttemptById(Long id) {
        TestAttempt testAttempt = testAttemptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestAttempt not found with id: " + id));
        return modelMapper.map(testAttempt, TestAttemptDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestAttemptDto> getAttemptsByUser(Long userId) {
        return testAttemptRepository.findByUserId(userId).stream()
                .map(attempt -> modelMapper.map(attempt, TestAttemptDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestAttemptDto> getAttemptsByMockTest(Long mockTestId) {
        return testAttemptRepository.findByMockTestId(mockTestId).stream()
                .map(attempt -> modelMapper.map(attempt, TestAttemptDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestAttemptDto> getAllTestAttempts() {
        return testAttemptRepository.findAll().stream()
                .map(attempt -> modelMapper.map(attempt, TestAttemptDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTestAttempt(Long id) {
        if (!testAttemptRepository.existsById(id)) {
            throw new ResourceNotFoundException("TestAttempt not found with id: " + id);
        }
        testAttemptRepository.deleteById(id);
    }
}