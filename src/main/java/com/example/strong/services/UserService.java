package com.example.strong.services;

import com.example.strong.entities.User;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateUserModel;

public interface UserService {
    String generatePassword();
    String generateUsername(String firstName, String lastName);
    boolean isUserExist(String username, String password);
    void changePassword(String username, String oldPassword, String newPassword);
    void validateFields(CreateUserModel createUserModel);
    void validateFields(UpdateUserModel updateUserModel);
    void activateByUsername(String username);
    void deactivateByUsername(String username);
    String getUniqueUsername(String username);
    User getEntityByUsername(String username);
    String encode(String password);
}
