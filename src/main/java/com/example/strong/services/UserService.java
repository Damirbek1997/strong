package com.example.strong.services;

public interface UserService {
    String generatePassword();
    String generateUsername(String firstName, String lastName);
    boolean isUserExist(String username, String password);
    void changePassword(String username, String oldPassword, String newPassword);
}
