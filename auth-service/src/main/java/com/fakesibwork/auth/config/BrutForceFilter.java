package com.fakesibwork.auth.config;

import com.fakesibwork.auth.service.LoginAttemptService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class BrutForceFilter extends OncePerRequestFilter {

    private final LoginAttemptService loginAttemptService;

    public BrutForceFilter(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        if (username != null && loginAttemptService.isBlocked(username)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Account is temporarily locked due to too many failed login attempts.");
            return;
        }

        chain.doFilter(request, response);
    }
}
