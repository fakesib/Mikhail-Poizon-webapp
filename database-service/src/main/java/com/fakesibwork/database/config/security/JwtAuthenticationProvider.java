package com.fakesibwork.database.config.security;

import com.fakesibwork.database.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("JWT AUTHENTICATION PROVIDER");
        if (!jwtService.isTokenValid(authentication.getCredentials().toString())) {
            System.out.println(authentication.getCredentials().toString());
            throw new RuntimeException("Invalid JWT");
        }

        return new JwtAuthenticationToken();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
