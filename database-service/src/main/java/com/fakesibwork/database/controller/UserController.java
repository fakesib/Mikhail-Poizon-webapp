package com.fakesibwork.database.controller;


import com.fakesibwork.common.exceptions.*;
import com.fakesibwork.database.service.UserService;
import com.fakesibwork.common.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addUser(@PathVariable String username,
                                    @RequestBody UserDto userDto) {
        try {
            userService.addUser(username, userDto);
            return ResponseEntity.ok("User added");
        } catch (UserIsPresentException | IncorrectRegistrationPathException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
                                        @RequestBody UserDto userDto) {

        var status = userService.updateUser(username, userDto);
        return ResponseEntity.status(status).build();
    }

    @GetMapping("confirm-mail/{token}")
    public ResponseEntity<?> confirmMailByVerifyToken(@PathVariable String token) {
        try {
            userService.confirmMail(token);
            return ResponseEntity.ok("Email is confirmed");
        } catch (EmailIsAlreadyConfirmedException | InvalidVerifyTokenException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
