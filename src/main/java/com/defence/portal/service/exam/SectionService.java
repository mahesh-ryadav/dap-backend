package com.defence.portal.service.exam;

import com.defence.portal.dto.exam.SectionDto;
import com.defence.portal.entity.exam.Section;
import java.util.List;

public interface SectionService {

    // Create a new section
    Section createSection(SectionDto sectionDto);

    // Update existing section
    Section updateSection(Long id, SectionDto sectionDto);

    // Get section by ID
    SectionDto getSectionById(Long id);

    // Get all sections
    List<SectionDto> getAllSections();

    // Get sections by mock test ID
    List<SectionDto> getSectionsByMockTestId(Long mockTestId);

    // Delete section
    void deleteSection(Long id);
}