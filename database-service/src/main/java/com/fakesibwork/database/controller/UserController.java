package com.fakesibwork.database.controller;

import com.fakesibwork.database.service.UserService;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/add")
    public void updateUserByUsername(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

}
