package com.fakesibwork.auth.service;

import com.fakesibwork.common.dto.Role;
import com.fakesibwork.common.dto.UserDto;
import com.fakesibwork.common.exceptions.ConfirmMailException;
import com.fakesibwork.common.exceptions.RegistrationException;
import com.fakesibwork.common.exceptions.UserIsPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private static final String USER_SERVICE_URL = "http://database-service:8083/api/user/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(String username, String password) throws RegistrationException {
        try {
            restTemplate.postForEntity(USER_SERVICE_URL + username, UserDto.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(null)
                    .role(Role.USER)
                    .build(), String.class); //TODO ApiResponse
        } catch (RestClientException exception) {
            throw new RegistrationException(exception.toString());
        }
    }

    public void confirmMail(String verifyToken) throws ConfirmMailException {
        try {
            var response = restTemplate.getForEntity(USER_SERVICE_URL + "confirm-mail/" + verifyToken, String.class);
        } catch (RestClientException exception) {
            throw new ConfirmMailException(exception.getMessage());
        }
    }
}
