package com.dto.dto.configs;

import com.dto.dto.model.Role;
import com.dto.dto.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    RoleRepository roleRepository;
    @Bean
    public CommandLineRunner loadRoles(RoleRepository roleRepository) {
        return args -> {
                if (!roleRepository.findByName("ROLE_USER").equals("ROLE_USER")) {
                    roleRepository.save(new Role(null, "ROLE_USER", null));
                }
                if (!roleRepository.findByName("ROLE_ADMIN").equals("ROLE_ADMIN")) {
                    roleRepository.save(new Role(null, "ROLE_ADMIN", null));
                }

        };
    }
}