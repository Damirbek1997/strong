package com.example.strong.services;

public interface BruteForceProtectService {
    void loginFailed(String key);
    void resetCache(String key);
    boolean isBlocked(String key);
}
