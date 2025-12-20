package com.defence.portal.repository.exam;

import com.defence.portal.entity.exam.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> findByMockTestId(Long mockTestId);
}
