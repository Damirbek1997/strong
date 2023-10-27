package com.example.strong.services;

import com.example.strong.entities.User;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateUserModel;

public interface UserService {
    User create(CreateUserModel createUserModel);
    User update(UpdateUserModel updateUserModel);
    void changePassword(UserCredentialsModel userCredentialsModel);
    void activateById(Long id);
    void deactivateById(Long id);
}
