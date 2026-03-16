package com.defence.portal.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String fullName;
    private String email;
    private String username;
    private java.util.List<String> roles;
    private long totalTestsAttempted;
}
