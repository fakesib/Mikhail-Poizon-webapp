package com.fakesibwork.admin_service.controller;

import com.fakesibwork.admin_service.service.UserService;
import com.fakesibwork.common.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String adminPage(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "admin";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        List<UserDto> list = userService.getAllUsers();
        model.addAttribute("users", list);
        return "users";
    }

}
