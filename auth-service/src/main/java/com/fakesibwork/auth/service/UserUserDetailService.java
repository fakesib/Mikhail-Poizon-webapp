package com.fakesibwork.auth.service;

import com.fakesibwork.auth.dto.UserDTO;
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

        UserDTO user = restTemplate.getForEntity(USER_SERVICE_URL + username, UserDTO.class).getBody();
        if (user.username == null) {
            throw new RuntimeException("User not found");
        }
        return builder()
                .username(user.username)
                .password(user.password)
                .roles(user.role)
                .build();
    }
}
