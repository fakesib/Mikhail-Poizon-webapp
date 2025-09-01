package com.fakesibwork.auth.controller;

import com.fakesibwork.auth.service.AuthService;
import com.fakesibwork.common.exceptions.ConfirmMailException;
import com.fakesibwork.common.exceptions.RegistrationException;
import com.fakesibwork.common.exceptions.UserIsPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("/auth/login?registrated=true"))
                    .build();
        } catch (RegistrationException exception) {
            return ResponseEntity.badRequest().body("User cannot be registered" + exception.getMessage());
        }
    }

    @GetMapping("/confirm-mail/{verify_token}")
    public ResponseEntity<?> confirmMail(@PathVariable String verify_token) {
        try {
            authService.confirmMail(verify_token);
            return ResponseEntity.ok("Email is confirmed");
        } catch (ConfirmMailException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}