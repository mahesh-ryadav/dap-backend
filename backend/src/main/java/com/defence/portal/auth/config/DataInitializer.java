package com.defence.portal.auth.config;

import com.defence.portal.auth.entity.ERole;
import com.defence.portal.auth.entity.Role;
import com.defence.portal.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
    }

    private void seedRoles() {
        for (ERole eRole : ERole.values()) {
            if (roleRepository.findByName(eRole).isEmpty()) {
                Role role = new Role();
                role.setName(eRole);
                roleRepository.save(role);
            }
        }
    }
}
