package com.example.strong.services.impl;

import com.example.strong.entities.User;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.repository.UserRepository;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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

    @Override
    public boolean isUserExist(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user != null;
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsernameAndPassword(username, oldPassword);

        if (user == null) {
            log.error("Incorrect username or password!");
            throw new BadRequestException("Incorrect username or password!");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        log.debug("Changed password to User with username: {}", user.getUsername());
    }
}
