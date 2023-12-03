package com.example.strong.controllers.impl;

import com.example.strong.controllers.UserController;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public void changeCredentials(String username, String oldPassword, String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    @Override
    public void updateStatus(String username, Boolean active) {
        if (active) {
            userService.activateByUsername(username);
        } else {
            userService.deactivateByUsername(username);
        }
    }
}
