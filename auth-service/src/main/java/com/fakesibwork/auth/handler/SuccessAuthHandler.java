package com.fakesibwork.auth.handler;

import com.fakesibwork.auth.service.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SuccessAuthHandler extends SimpleUrlAuthenticationSuccessHandler {

    private LoginAttemptService loginAttemptService;

    public SuccessAuthHandler(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        loginAttemptService.loginSucceeded(authentication.getName());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
