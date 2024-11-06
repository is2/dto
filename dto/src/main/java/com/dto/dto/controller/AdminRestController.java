package com.dto.dto.controller;

import com.dto.dto.model.User;
import com.dto.dto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> currentUser = userService.findByUsername(username);

        return currentUser
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestParam("rolee") List<String> roleName) {
        userService.saveUser(user, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody User user, @RequestParam("rolee") List<String> roleName) {
        userService.updateUser(user, roleName);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
