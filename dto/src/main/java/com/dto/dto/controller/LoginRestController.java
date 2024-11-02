package com.dto.dto.controller;

import com.dto.dto.model.User;
import com.dto.dto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginRestController {

    private final UserService userService;

    @Autowired
    public LoginRestController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(value = "error", required = false) String error,
                                        @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
        if (logout != null) {
            return ResponseEntity.ok("You have been logged out successfully.");
        }
        return ResponseEntity.ok("Login page");
    }

    @GetMapping("/registration")
    public ResponseEntity<User> getRegistrationForm() {
        return ResponseEntity.ok(new User());
    }

    @PostMapping("/registration")
    public ResponseEntity<String> addNewUser(@RequestBody User user,
                                             @RequestParam("rolee") String roleName) {
        userService.saveUser(user, roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}

