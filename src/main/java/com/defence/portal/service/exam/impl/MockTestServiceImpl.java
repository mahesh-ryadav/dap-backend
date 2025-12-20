package com.defence.portal.service.exam.impl;

import com.defence.portal.dto.exam.MockTestDto;
import com.defence.portal.entity.exam.MockTest;
import com.defence.portal.exception.ResourceNotFoundException;
import com.defence.portal.repository.exam.MockTestRepository;
import com.defence.portal.service.exam.MockTestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MockTestServiceImpl implements MockTestService {

    private final MockTestRepository mockTestRepository;
    private final ModelMapper modelMapper;

    public MockTestServiceImpl(MockTestRepository mockTestRepository, ModelMapper modelMapper) {
        this.mockTestRepository = mockTestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MockTest createMockTest(MockTestDto mockTestDto) {
        MockTest mockTest = modelMapper.map(mockTestDto, MockTest.class);
        mockTest.setStatus("DRAFT"); // Default status
        return mockTestRepository.save(mockTest);
    }

    @Override
    public MockTest updateMockTest(Long id, MockTestDto mockTestDto) {
        MockTest existingMockTest = mockTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MockTest not found with id: " + id));

        modelMapper.map(mockTestDto, existingMockTest);
        return mockTestRepository.save(existingMockTest);
    }

    @Override
    @Transactional(readOnly = true)
    public MockTestDto getMockTestById(Long id) {
        MockTest mockTest = mockTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MockTest not found with id: " + id));
        return modelMapper.map(mockTest, MockTestDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockTestDto> getAllMockTests() {
        return mockTestRepository.findAll().stream()
                .map(mockTest -> modelMapper.map(mockTest, MockTestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockTestDto> getPublishedMockTests() {
        return mockTestRepository.findByStatus("PUBLISHED").stream()
                .map(mockTest -> modelMapper.map(mockTest, MockTestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MockTestDto> getMockTestsByExamType(String examType) {
        return mockTestRepository.findByExamType(examType).stream()
                .map(mockTest -> modelMapper.map(mockTest, MockTestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMockTest(Long id) {
        if (!mockTestRepository.existsById(id)) {
            throw new ResourceNotFoundException("MockTest not found with id: " + id);
        }
        mockTestRepository.deleteById(id);
    }

    @Override
    public MockTest publishMockTest(Long id) {
        MockTest mockTest = mockTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MockTest not found with id: " + id));
        mockTest.setStatus("PUBLISHED");
        return mockTestRepository.save(mockTest);
    }
}