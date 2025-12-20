package com.defence.portal.service.exam.impl;

import com.defence.portal.dto.exam.SectionDto;
import com.defence.portal.entity.exam.MockTest;
import com.defence.portal.entity.exam.Section;
import com.defence.portal.exception.ResourceNotFoundException;
import com.defence.portal.repository.exam.MockTestRepository;
import com.defence.portal.repository.exam.SectionRepository;
import com.defence.portal.service.exam.SectionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final MockTestRepository mockTestRepository;
    private final ModelMapper modelMapper;

    public SectionServiceImpl(SectionRepository sectionRepository,
                              MockTestRepository mockTestRepository,
                              ModelMapper modelMapper) {
        this.sectionRepository = sectionRepository;
        this.mockTestRepository = mockTestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Section createSection(SectionDto sectionDto) {
        Section section = modelMapper.map(sectionDto, Section.class);

        // Set the mock test if mockTestId is provided
        if (sectionDto.getMockTestId() != null) {
            MockTest mockTest = mockTestRepository.findById(sectionDto.getMockTestId())
                    .orElseThrow(() -> new ResourceNotFoundException("MockTest not found with id: " + sectionDto.getMockTestId()));
            section.setMockTest(mockTest);
        }

        return sectionRepository.save(section);
    }

    @Override
    public Section updateSection(Long id, SectionDto sectionDto) {
        Section existingSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + id));

        modelMapper.map(sectionDto, existingSection);

        // Update mock test if changed
        if (sectionDto.getMockTestId() != null) {
            MockTest mockTest = mockTestRepository.findById(sectionDto.getMockTestId())
                    .orElseThrow(() -> new ResourceNotFoundException("MockTest not found with id: " + sectionDto.getMockTestId()));
            existingSection.setMockTest(mockTest);
        }

        return sectionRepository.save(existingSection);
    }

    @Override
    @Transactional(readOnly = true)
    public SectionDto getSectionById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + id));
        return modelMapper.map(section, SectionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectionDto> getAllSections() {
        return sectionRepository.findAll().stream()
                .map(section -> modelMapper.map(section, SectionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectionDto> getSectionsByMockTestId(Long mockTestId) {
        return sectionRepository.findByMockTestId(mockTestId).stream()
                .map(section -> modelMapper.map(section, SectionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Section not found with id: " + id);
        }
        sectionRepository.deleteById(id);
    }
}