package com.example.strong.services;

import com.example.strong.entities.User;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateUserModel;

public interface UserService {
    User create(CreateUserModel createUserModel);
    User update(UpdateUserModel updateUserModel);
    String changePassword(UserCredentialsModel userCredentialsModel);
    String changeStatus(Long id, Boolean isActive);
}
