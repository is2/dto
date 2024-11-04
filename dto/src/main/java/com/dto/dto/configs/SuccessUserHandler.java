package com.dto.dto.configs;

import com.dto.dto.model.Role;
import com.dto.dto.model.User;
import com.dto.dto.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Autowired
    private final UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<String> roles = currentUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()).toString())
                .collect(Collectors.toList());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else   {
            System.out.println(roles);
            httpServletResponse.sendRedirect("/user");
        }

    }
}