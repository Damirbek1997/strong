package com.gym.strong.services;

public interface UserService {
    String generateAndCheckUsername(String firstName, String lastName);
    String generatePassword();
    String generateAndCheckUsername(String firstName, String lastName, String oldFirstName, String oldLastName);
}
