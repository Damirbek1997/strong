package com.example.strong.services.impl;

import com.example.strong.entities.User;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateUserModel;
import com.example.strong.repository.UserRepository;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(CreateUserModel createUserModel) {
        User user = new User();
        user.setFirstName(createUserModel.getFirstName());
        user.setLastName(createUserModel.getLastName());
        user.setUsername(generateUsername(createUserModel.getFirstName(), createUserModel.getLastName()));
        user.setPassword(generatePassword());
        user.setIsActive(true);

        Long amountOfUsers = userRepository.countByUsernameLike(user.getUsername());

        if (amountOfUsers > 0) {
            user.setUsername(user.getUsername() + amountOfUsers);
        }

        User savedUser = userRepository.save(user);
        log.info("Created User with model {}", createUserModel);
        return savedUser;
    }

    @Override
    public User update(UpdateUserModel updateUserModel) {
        User user = getEntityById(updateUserModel.getId());

        if (updateUserModel.getFirstName() != null) {
            user.setFirstName(updateUserModel.getFirstName());
        }

        if (updateUserModel.getLastName() != null) {
            user.setLastName(updateUserModel.getLastName());
        }

        String username = generateUsername(user.getFirstName(), user.getLastName());
        Long amountOfUsers = userRepository.countByUsernameLike(username);

        if (amountOfUsers > 0) {
            user.setUsername(username + amountOfUsers);
        }

        User savedUser = userRepository.save(user);
        log.info("Updated User with model {}", updateUserModel);
        return savedUser;
    }

    @Override
    @Transactional
    public void changePassword(UserCredentialsModel userCredentialsModel) {
        User user = getEntityById(userCredentialsModel.getId());
        user.setPassword(userCredentialsModel.getNewPassword());
        userRepository.save(user);
        log.debug("Changed password to User with username: {}", user.getUsername());
    }

    @Override
    @Transactional
    public void activateById(Long id) {
        User user = getEntityById(id);
        user.setIsActive(true);
        userRepository.save(user);
        log.debug("Activated User with username: {}", user.getUsername());
    }

    @Override
    @Transactional
    public void deactivateById(Long id) {
        User user = getEntityById(id);
        user.setIsActive(false);
        userRepository.save(user);
        log.debug("Deactivated User with username: {}", user.getUsername());
    }

    private User getEntityById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        log.error("There is no User with id {}", id);
        throw new BadRequestException("There is no User with id: " + id);
    }

    private String generateUsername(String firstName, String lastName) {
        return firstName + "." + lastName;
    }

    private String generatePassword() {
        Random random = new Random();
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            password.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return password.toString();
    }
}
