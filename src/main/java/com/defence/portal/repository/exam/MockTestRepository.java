package com.defence.portal.repository.exam;

import com.defence.portal.entity.exam.MockTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockTestRepository extends JpaRepository<MockTest, Long> {

    // Get only published tests
    List<MockTest> findByStatus(String status);

    // Filter by exam type (BSF, SSC, NDA)
    List<MockTest> findByExamType(String examType);
}
