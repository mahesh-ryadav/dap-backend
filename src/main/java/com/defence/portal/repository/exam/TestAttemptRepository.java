package com.defence.portal.repository.exam;

import com.defence.portal.entity.exam.TestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {

    List<TestAttempt> findByUserId(Long userId);

    List<TestAttempt> findByMockTestId(Long mockTestId);

    Optional<TestAttempt> findByUserIdAndMockTestIdAndStatus(
            Long userId,
            Long mockTestId,
            String status
    );
}
