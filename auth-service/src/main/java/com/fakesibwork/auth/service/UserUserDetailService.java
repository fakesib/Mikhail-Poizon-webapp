package com.fakesibwork.auth.service;

import com.fakesibwork.common.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import static org.springframework.security.core.userdetails.User.builder;

@Component
public class UserUserDetailService implements UserDetailsService {

    private static final String USER_SERVICE_URL = "http://database-service:8083/api/user/";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = restTemplate.getForEntity(USER_SERVICE_URL + username, UserDto.class).getBody();
        if (user.getUsername() == null) {
            throw new RuntimeException("User not found");
        }
        return builder()
                .username(user.getUsername())
                .password(user.getUsername())
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}
