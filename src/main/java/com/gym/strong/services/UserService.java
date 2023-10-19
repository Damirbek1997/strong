package com.gym.strong.services;

public interface UserService {
    String regenerateUsername(String firstName, String lastName, Long count);
    String regenerateUsername(String firstName, String lastName, String oldFirstName, String oldLastName);
}
