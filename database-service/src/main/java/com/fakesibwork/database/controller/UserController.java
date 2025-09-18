package com.fakesibwork.database.controller;


import com.fakesibwork.common.exceptions.*;
import com.fakesibwork.database.service.UserService;
import com.fakesibwork.common.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        } catch (UserIsPresentException | IncorrectUsernameException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
                                        @RequestBody UserDto userDto) {

        try {
            userService.updateUser(username, userDto);
            return ResponseEntity.ok("User updated");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/all")
    public List<UserDto> allUsers() {

        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            return null;
        }
    }


    //TODO Change path
    @GetMapping({"confirm-mail", "confirm-mail/{token}"})
    public ResponseEntity<?> confirmMailByVerifyToken(@PathVariable(required = false) String token) {
        try {
            userService.confirmMail(token);
            return ResponseEntity.ok("Email is confirmed");
        } catch (EmailIsAlreadyConfirmedException | InvalidVerifyTokenException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
