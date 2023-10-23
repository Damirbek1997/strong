package com.example.strong.services.impl;

import com.example.strong.entities.User;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateUserModel;
import com.example.strong.repository.UserRepository;
import com.example.strong.services.UserService;
import com.example.strong.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        user.setUsername(UserUtil.generateUsername(createUserModel.getFirstName(), createUserModel.getLastName()));
        user.setPassword(UserUtil.generatePassword());
        user.setIsActive(true);

        if (isUsernameBusy(user.getUsername())) {
            user.setUsername(regenerateUsername(user.getFirstName(), user.getLastName(), 1L));
        }

        User savedUser = userRepository.save(user);
        log.info("Created User with model {}", createUserModel);
        return savedUser;
    }

    @Override
    public User update(UpdateUserModel updateUserModel) {
        User user = getEntityById(updateUserModel.getId());
        boolean regenerateUsername = false;

        if (updateUserModel.getFirstName() == null) {
            user.setLastName(updateUserModel.getLastName());
            regenerateUsername = true;
        }

        if (updateUserModel.getLastName() == null) {
            user.setFirstName(updateUserModel.getFirstName());
            regenerateUsername = true;
        }

        if (regenerateUsername) {
            user.setUsername(UserUtil.generateUsername(user.getFirstName(), user.getLastName()));

            if (isUsernameBusy(user.getUsername())) {
                user.setUsername(regenerateUsername(user.getFirstName(), user.getLastName(), 1L));
            }
        }

        User savedUser = userRepository.save(user);
        log.info("Updated User with model {}", updateUserModel);
        return savedUser;
    }

    @Override
    @Transactional
    public String changePassword(UserCredentialsModel userCredentialsModel) {
        User user = getEntityById(userCredentialsModel.getId());
        user.setPassword(userCredentialsModel.getNewPassword());
        userRepository.save(user);
        return "OK";
    }

    @Override
    @Transactional
    public String changeStatus(Long id, Boolean isActive) {
        User user = getEntityById(id);
        user.setIsActive(isActive);
        userRepository.save(user);
        return "OK";
    }

    private String regenerateUsername(String firstName, String lastName, Long count) {
        String username = firstName + "." + lastName + count;

        if (isUsernameBusy(username)) {
            count++;
            regenerateUsername(firstName, lastName, count);
        }

        return username;
    }

    private boolean isUsernameBusy(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private User getEntityById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        log.error("There is no User with id {}", id);
        throw new BadRequestException("There is no User with id: " + id);
    }
}
