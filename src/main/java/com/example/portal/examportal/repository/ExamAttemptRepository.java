package com.example.portal.examportal.repository;

import com.example.portal.examportal.entity.AttemptStatus;
import com.example.portal.examportal.entity.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
    Optional<ExamAttempt> findByUserIdAndExamIdAndStatus(Long userId, Long examId, AttemptStatus status);

    List<ExamAttempt> findByUserIdAndExamId(Long userId, Long examId);
}
