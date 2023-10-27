package com.example.strong.services.impl;

import com.example.strong.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String generateUsername(String firstName, String lastName) {
        return firstName + "." + lastName;
    }

    @Override
    public String generatePassword() {
        Random random = new Random();
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            password.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return password.toString();
    }
}
