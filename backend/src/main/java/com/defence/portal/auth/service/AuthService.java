package com.defence.portal.auth.service;

import com.defence.portal.auth.dto.AuthResponse;
import com.defence.portal.auth.dto.LoginRequest;
import com.defence.portal.auth.dto.RegisterRequest;
import com.defence.portal.auth.entity.ERole;
import com.defence.portal.auth.entity.Role;
import com.defence.portal.auth.entity.User;
import com.defence.portal.auth.repository.RoleRepository;
import com.defence.portal.auth.repository.UserRepository;
import com.defence.portal.auth.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder encoder;
        private final JwtUtils jwtUtils;

        public AuthResponse authenticateUser(LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                                .map(item -> item.getAuthority())
                                .collect(Collectors.toList());

                return new AuthResponse(jwt,
                                userDetails.getId(),
                                userDetails.getUsername(),
                                userDetails.getEmail(),
                                roles);
        }

        public void registerUser(RegisterRequest signUpRequest) {
                if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                        throw new RuntimeException("Error: Username is already taken!");
                }

                if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                        throw new RuntimeException("Error: Email is already in use!");
                }

                // Create new user's account
                User user = new User(null, signUpRequest.getUsername(),
                                signUpRequest.getEmail(),
                                encoder.encode(signUpRequest.getPassword()),
                                signUpRequest.getFullName(),
                                null);

                Set<String> strRoles = signUpRequest.getRole();
                Set<Role> roles = new HashSet<>();

                if (strRoles == null) {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Error: Role ROLE_USER is not found in database."));
                        roles.add(userRole);
                } else {
                        strRoles.forEach(role -> {
                                switch (role.toLowerCase()) {
                                        case "admin":
                                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role ROLE_ADMIN is not found in database."));
                                                roles.add(adminRole);
                                                break;
                                        case "instructor":
                                                Role modRole = roleRepository.findByName(ERole.ROLE_INSTRUCTOR)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role ROLE_INSTRUCTOR is not found in database."));
                                                roles.add(modRole);
                                                break;
                                        default:
                                                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error: Role ROLE_USER is not found in database."));
                                                roles.add(userRole);
                                }
                        });
                }

                user.setRoles(roles);
                userRepository.save(user);
        }
}
