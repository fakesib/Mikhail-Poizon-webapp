package com.fakesibwork.auth.service;

import dto.Role;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;

@Service
public class AuthService {

    private static final String USER_SERVICE_URL = "http://database-service:8083/api/user/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(String username, String password) {
        restTemplate.postForEntity(USER_SERVICE_URL + username, UserDto.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .email(null)
                        .role(Role.USER)
                .build(), UserDto.class);
    }

    public HttpStatus confirmMail(String verifyToken) {
        var response = restTemplate.getForEntity(USER_SERVICE_URL + "confirm-mail/" + verifyToken, HttpStatus.class);
        return response.getBody();
    }
}
