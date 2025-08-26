package com.fakesibwork.auth.service;

import dto.Role;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private static final String USER_SERVICE_URL = "http://database-service:8083/api/user/add";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(String username, String password) {
        restTemplate.postForEntity(USER_SERVICE_URL, UserDto.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .role(Role.USER)
                .build(), UserDto.class);
    }
}
