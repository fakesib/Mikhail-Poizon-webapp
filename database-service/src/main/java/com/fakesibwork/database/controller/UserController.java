package com.fakesibwork.database.controller;

import com.fakesibwork.database.service.UserService;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("confirm-mail/{verify_token}")
    public HttpStatus confirmMailByVerifyToken(@PathVariable String verify_token) {
        System.out.println(verify_token);
        try {
            userService.confirmMail(verify_token);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/add")
    public void updateUserByUsername(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

}
