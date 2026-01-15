package com.defence.portal.auth.service;

import com.defence.portal.auth.dto.UserProfileDTO;
import com.defence.portal.examportal.repository.ExamAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final ExamAttemptRepository examAttemptRepository;

    public UserProfileDTO getCurrentUserProfile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        long count = examAttemptRepository.countByUserId(userDetails.getId());

        return UserProfileDTO.builder()
                .fullName(userDetails.getFullName())
                .email(userDetails.getEmail())
                .username(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList()))
                .totalTestsAttempted(count)
                .build();
    }
}
