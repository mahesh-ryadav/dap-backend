package com.defence.portal.repository.exam;

import com.defence.portal.entity.exam.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    List<UserAnswer> findByTestAttemptId(Long attemptId);
}
