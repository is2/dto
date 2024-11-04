package com.dto.dto.configs;

import com.dto.dto.model.Role;
import com.dto.dto.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadRoles(RoleRepository roleRepository) {
        return args -> {
                roleRepository.save(new Role(null, "ROLE_USER", null));

                roleRepository.save(new Role(null, "ROLE_ADMIN", null));

        };
    }
}