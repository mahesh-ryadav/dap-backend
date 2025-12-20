package com.defence.portal.repository.exam;

import com.defence.portal.entity.exam.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByTestAttemptId(Long attemptId);
}
