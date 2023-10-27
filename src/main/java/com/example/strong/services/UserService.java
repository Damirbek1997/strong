package com.example.strong.services;

public interface UserService {
    String generatePassword();
    String generateUsername(String firstName, String lastName);
}
