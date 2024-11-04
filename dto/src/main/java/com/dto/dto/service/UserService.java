package com.dto.dto.service;

import com.dto.dto.model.Role;
import com.dto.dto.model.User;
import com.dto.dto.repository.RoleRepository;
import com.dto.dto.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String roleName) {
        Role role = new Role();
        role.setName(roleName);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(role));
        System.out.println(role.getName());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        userRepository.save(user);
    }
    public void updateUser(User user, String roleName) {
        Role role = new Role();
        role.setName(roleName);
        user.setRoles(Set.of(role));
        System.out.println(role.getName());
        System.out.println(user.getUsername());
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


