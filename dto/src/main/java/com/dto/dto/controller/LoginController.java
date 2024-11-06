package com.dto.dto.controller;
import com.dto.dto.model.Role;
import com.dto.dto.model.User;
import com.dto.dto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been logged out successfully.");
        }


        return "login";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userFormRegistration", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("userFormRegistration") User user,
                             @RequestParam("rolee") List<String> roleName) {

        userService.saveUser(user, roleName);
        return "redirect:/login";
    }

}

