package com.fakesibwork.database.controller;

import com.fakesibwork.database.exception.UserIsPresentException;
import com.fakesibwork.database.service.UserService;
import dto.UserDto;
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
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        try {
            userService.addUser(userDto);
            return ResponseEntity.ok("User added");
        } catch (UserIsPresentException e) {
            return ResponseEntity.status(400).body("User is already presents");
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

        var status = userService.confirmMail(token);
        return ResponseEntity.status(status).build();
    }

}
