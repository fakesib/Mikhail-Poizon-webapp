package com.fakesibwork.auth.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private final Integer MAX_ATTEMPTS = 5;

    private final Map<String, Integer> attemptsMap = new ConcurrentHashMap<>();

    public boolean loginValid(String username) {
        return attemptsMap.get(username) < MAX_ATTEMPTS;
    }

    public void loginSucceeded(String key) {
        attemptsMap.remove(key);
    }

    public void loginFailed(String key) {
        int attempts = attemptsMap.getOrDefault(key, 0);
        attempts++;
        attemptsMap.put(key, attempts);
    }
}
