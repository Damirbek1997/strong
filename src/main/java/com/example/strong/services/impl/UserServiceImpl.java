package com.example.strong.services.impl;

import com.example.strong.entities.User;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateUserModel;
import com.example.strong.repository.UserRepository;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

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
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new BadRequestException("Incorrect username or password!");
        }

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("Old password does not match!");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        log.debug("Changed password to User with username: {}", user.getUsername());
    }

    @Override
    public void validateFields(CreateUserModel createUserModel) {
        if (createUserModel.getFirstName() == null) {
            throw new BadRequestException("firstName must be filled!");
        }

        if (createUserModel.getLastName() == null) {
            throw new BadRequestException("lastName must be filled!");
        }
    }

    @Override
    public void validateFields(UpdateUserModel updateUserModel) {
        if (updateUserModel.getFirstName() == null) {
            throw new BadRequestException("firstName must be filled!");
        }

        if (updateUserModel.getLastName() == null) {
            throw new BadRequestException("lastName must be filled!");
        }

        if (updateUserModel.getActive() == null) {
            throw new BadRequestException("active must be filled!");
        }
    }

    @Override
    public void activateByUsername(String username) {
        User user = getEntityByUsername(username);
        user.setActive(true);
        userRepository.save(user);
        log.debug("Activated User with username: {}", username);
    }

    @Override
    public void deactivateByUsername(String username) {
        User user = getEntityByUsername(username);
        user.setActive(false);
        userRepository.save(user);
        log.debug("Activated User with username: {}", username);
    }

    @Override
    public String getUniqueUsername(String username) {
        String usernameWithPercent = username + "%";
        Long amountOfUsers = userRepository.countByUsernameLike(usernameWithPercent);

        if (amountOfUsers > 0) {
            username = username + amountOfUsers;
        }

        return username;
    }

    @Override
    public User getEntityByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new BadRequestException("There is no user with username: " + username);
        }

        return user;
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }
}
