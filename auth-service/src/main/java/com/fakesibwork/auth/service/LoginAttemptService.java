package com.fakesibwork.auth.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private final Integer MAX_ATTEMPTS = 5;
    private final long LOCK_TIME = 15 * 60 * 1000;

    private final Map<String, Integer> attemptsMap = new ConcurrentHashMap<>();
    private final Map<String, Long> lockTimeMap = new ConcurrentHashMap<>();

    public void loginSucceeded(String key) {
        attemptsMap.remove(key);
        lockTimeMap.remove(key);
    }

    public void loginFailed(String key) {
        int attempts = attemptsMap.getOrDefault(key, 0) + 1;
        attemptsMap.put(key, attempts);

        if (attempts >= MAX_ATTEMPTS) {
            lockTimeMap.put(key, System.currentTimeMillis() + LOCK_TIME);
        }
    }

    public boolean isBlocked(String key) {
        Long lockTime = lockTimeMap.get(key);
        if (lockTime == null) return false;
        if (lockTime > System.currentTimeMillis()) {
            return true;
        } else {
            loginSucceeded(key);
            return false;
        }
    }

    public long getUnlockTime(String key) {
        return lockTimeMap.getOrDefault(key, 0L);
    }

    public void reset() {
        attemptsMap.clear();
        lockTimeMap.clear();
    }
}
