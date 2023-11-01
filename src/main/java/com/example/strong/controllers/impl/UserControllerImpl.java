package com.example.strong.controllers.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.controllers.UserController;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @PreAuthenticated
    public ResponseEntity<String> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PreAuthenticated
    public ResponseEntity<String> changeCredentials(String username,
                                                    String password,
                                                    String newPassword) {
        userService.changePassword(username, password, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
