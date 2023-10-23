package com.gym.strong.services;

public interface UserService {
    String generateUsername(String firstName, String lastName);
    String generatePassword();
    String generateUsername(String firstName, String lastName, String oldFirstName, String oldLastName);
}
