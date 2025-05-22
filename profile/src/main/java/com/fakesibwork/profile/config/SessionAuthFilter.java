package com.fakesibwork.profile.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionAuthFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;

    public SessionAuthFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String sessionId = request.getHeader("X-Session-Id");

        if (sessionId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String key = "spring:session:sessions:" + sessionId;
            String securityContextBase64 = redisTemplate.opsForHash()
                    .get(key, "sessionAttr:SPRING_SECURITY_CONTEXT")
                    .toString();

            if (securityContextBase64 != null && !securityContextBase64.isEmpty()) {
                try {
                    byte[] data = Base64.getDecoder().decode(securityContextBase64);
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                    SecurityContext context = (SecurityContext) ois.readObject();
                    ois.close();

                    Authentication authentication = context.getAuthentication();
                    if (authentication != null && authentication.isAuthenticated()) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}