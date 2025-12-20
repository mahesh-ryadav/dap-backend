package com.defence.portal.repository.exam;

import com.defence.portal.entity.exam.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySectionId(Long sectionId);

    List<Question> findByDifficultyLevel(String difficultyLevel);
}
