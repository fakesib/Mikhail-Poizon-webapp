package com.fakesibwork.auth.handler;

import com.fakesibwork.auth.service.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
            // формируем строку с временем
            String msg = "Аккаунт временно заблокирован. Повторите попытку после: " +
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(unlockTime), ZoneId.systemDefault());

            // добавляем query параметр с сообщением
            setDefaultFailureUrl("/auth/login?error=" + URLEncoder.encode(msg, StandardCharsets.UTF_8));
        } else {
            setDefaultFailureUrl("/auth/login?error=Wrong+username+or+password");
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
