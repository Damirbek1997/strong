package com.example.strong.services;

import com.example.strong.entities.User;

public interface UserService {
    String generatePassword();
    String generateUsername(String firstName, String lastName);
    void changePassword(String username, String oldPassword, String newPassword);
    void activateByUsername(String username);
    void deactivateByUsername(String username);
    String getUniqueUsername(String username);
    User getEntityByUsername(String username);
}
