package com.fakesibwork.database.service;

import com.fakesibwork.database.dto.UserDTO;
import com.fakesibwork.database.exception.UserIsPresentException;
import com.fakesibwork.database.model.User;
import com.fakesibwork.database.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserDTO getUser(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(RuntimeException::new);
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public void addUser(UserDTO userDTO) {
        if (userRepo.findByUsername(userDTO.username).isEmpty()) {
            var user = new User();
            user.setUsername(userDTO.username);
            user.setPassword(userDTO.password);
            user.setRole(userDTO.role);
            userRepo.save(user);
        } else {
            throw new UserIsPresentException();
        }
    }
}
