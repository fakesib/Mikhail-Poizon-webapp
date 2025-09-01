package com.fakesibwork.auth.controller;

import com.fakesibwork.auth.service.AuthService;
import com.fakesibwork.common.exceptions.UserIsPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam("username") String username,
                                      @RequestParam("password") String password) {
        try {
            authService.register(username, password);
            return ResponseEntity.ok("User registered");
        } catch (UserIsPresentException e) {
            return ResponseEntity.badRequest().body("User cannot be registered" + e);
        }
    }

    @GetMapping("/confirm-mail/{verify_token}")
    public ResponseEntity<?> confirmMail(@PathVariable String verify_token) {
        var status = authService.confirmMail(verify_token);
        return ResponseEntity.status(status).build();
    }

}