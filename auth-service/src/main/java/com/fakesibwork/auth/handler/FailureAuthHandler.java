package com.fakesibwork.auth.handler;

import com.fakesibwork.auth.service.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class FailureAuthHandler extends SimpleUrlAuthenticationFailureHandler {

    private LoginAttemptService loginAttemptService;

    public FailureAuthHandler(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String username = request.getParameter("username");
        loginAttemptService.loginFailed(username);

        if (loginAttemptService.isBlocked(username)) {
            long unlockTime = loginAttemptService.getUnlockTime(username);
            request.setAttribute("error",
                    "Аккаунт временно заблокирован. Повторите попытку после: " +
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(unlockTime), ZoneId.systemDefault())
            );
        } else {
            request.setAttribute("error", "Неверное имя пользователя или пароль");
        }
        setDefaultFailureUrl("/auth/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
