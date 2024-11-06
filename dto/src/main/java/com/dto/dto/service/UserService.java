package com.dto.dto.service;

import com.dto.dto.model.Role;
import com.dto.dto.model.User;
import com.dto.dto.repository.RoleRepository;
import com.dto.dto.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    public void saveUser(User user, List<String> roles) {

        Set<Role> userRoles = roles.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        userRepository.save(user);
    }
    @Transactional
    public void updateUser(User updatedUser, List<String> roles) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Set<Role> userRoles = roles.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());

        updatedUser.setRoles(userRoles);

        if (updatedUser.getPassword().equals(existingUser.getPassword())) {
            System.out.println("Match");
        }else {
            System.out.println("Not match");
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        System.out.println(updatedUser.getUsername());
        System.out.println(updatedUser.getPassword());
        System.out.println(existingUser.getPassword());

        userRepository.save(updatedUser);
    }
    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        for (Role role : user.getRoles()) {
            role.getUsers().remove(user);
        }
        user.getRoles().clear();
        userRepository.delete(user);
    }
}


