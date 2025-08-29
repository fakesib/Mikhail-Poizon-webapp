package com.fakesibwork.database.controller;

import com.fakesibwork.database.service.UserService;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username,
                                             @RequestBody UserDto userDto) {

        var status = userService.updateUser(username, userDto);
        return new ResponseEntity<>(status);
    }

    @GetMapping("confirm-mail/{token}")
    public ResponseEntity<String> confirmMailByVerifyToken(@PathVariable String token) {

        var status = userService.confirmMail(token);
        return ResponseEntity.status(status).build();
    }

}
