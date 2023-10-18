package com.gym.strong.mappers.impl;

import com.gym.strong.entities.User;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.UserModel;
import com.gym.strong.models.crud.CreateUserModel;
import com.gym.strong.models.crud.UpdateUserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class UserMapper implements AbstractMapper<User, UserModel> {
    @Override
    public UserModel toModel(User entity) {
        UserModel userModel = new UserModel();
        userModel.setId(entity.getId());
        userModel.setFirstName(entity.getFirstName());
        userModel.setLastName(entity.getLastName());
        userModel.setUsername(entity.getUsername());
        userModel.setPassword(entity.getPassword());
        userModel.setIsActive(entity.getIsActive());
        return userModel;
    }

    @Override
    public User toEntity(UserModel model) {
        User user = new User();
        user.setId(model.getId());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setUsername(model.getUsername());
        user.setPassword(model.getPassword());
        user.setIsActive(model.getIsActive());
        return user;
    }

    public User toEntity(CreateUserModel model) {
        User user = new User();
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setUsername(generateUsername(model.getFirstName(), model.getLastName()));
        user.setPassword(generatePassword());
        user.setIsActive(model.getIsActive());
        return user;
    }

    public User fromString(String line) {
        String[] parts = line.split(",");

        if (parts.length >= 4) {
            User user = new User();
            user.setFirstName(parts[1]);
            user.setLastName(parts[2]);
            user.setUsername(parts[3]);
            user.setPassword(parts[4]);
            user.setIsActive(Boolean.valueOf(parts[4]));

            return user;
        }

        throw new InsertStorageFromFileException("Error while parsing string to entity: all fields of User must be filled!");
    }

    public User updateUserData(User user, UpdateUserModel updateUserModel) {
        if (updateUserModel.getFirstName() != null) {
            user.setFirstName(updateUserModel.getFirstName());
        }

        if (updateUserModel.getLastName() != null) {
            user.setLastName(updateUserModel.getLastName());
        }

        if (updateUserModel.getFirstName() != null && updateUserModel.getLastName() != null) {
            user.setUsername(generateUsername(updateUserModel.getFirstName(), updateUserModel.getLastName()));
        }

        if (updateUserModel.getIsActive() != null) {
            user.setIsActive(updateUserModel.getIsActive());
        }

        return user;
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
