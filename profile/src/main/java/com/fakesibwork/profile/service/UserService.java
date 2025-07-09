package com.fakesibwork.profile.service;

import com.fakesibwork.profile.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User getUser(String name) {
        return userRepo.findByUsername(name);
    }
}
