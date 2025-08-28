package com.fakesibwork.database.service;

import dto.UserDto;
import com.fakesibwork.database.exception.UserIsPresentException;
import com.fakesibwork.database.model.User;
import com.fakesibwork.database.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserDto getUser(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(RuntimeException::new);
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .verify_token(user.getVerify_token())
                .build();
    }

    public void addUser(UserDto userDto) {
        if (userRepo.findByUsername(userDto.getUsername()).isEmpty()) {
            var user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setRole(userDto.getRole());
            user.setVerify_token(userDto.getVerify_token());
            userRepo.save(user);
        } else {
            throw new UserIsPresentException();
        }
    }


    public void confirmMail(String verifyToken) {
        System.out.println(verifyToken);
        userRepo.updateVerifyToken(verifyToken);
    }
}
