package com.defence.portal.auth.service;

import com.defence.portal.auth.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtils jwtUtils;

    public String generateToken(Authentication authentication) {
        return jwtUtils.generateJwtToken(authentication);
    }

    public boolean validateToken(String token) {
        return jwtUtils.validateJwtToken(token);
    }

    public String getUsernameFromToken(String token) {
        return jwtUtils.getUserNameFromJwtToken(token);
    }
}
