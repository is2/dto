package com.dto.dto.controller;

import com.dto.dto.model.User;
import com.dto.dto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @GetMapping
    public String userPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("user", currentUser);
        String roleName = currentUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()).toString())
                .collect(Collectors.toList()).get(0);
        model.addAttribute("roleName", roleName);
        return "user_panel";
    }
}


