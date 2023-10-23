package com.example.strong.mappers.impl;

import com.example.strong.entities.User;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        user.setIsActive(model.getIsActive());
        return user;
    }
}
