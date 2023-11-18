package com.example.strong.controllers.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.controllers.UserController;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @PreAuthenticated
    public void login() {
        // the business logic in HasRequestPermissionAspect
    }

    @Override
    @PreAuthenticated
    public void changeCredentials(String username, String password, String newPassword) {
        userService.changePassword(username, password, newPassword);
    }

    @Override
    @PreAuthenticated
    public void updateStatus(String username, Boolean active) {
        if (active) {
            userService.activateByUsername(username);
        } else {
            userService.deactivateByUsername(username);
        }
    }
}
