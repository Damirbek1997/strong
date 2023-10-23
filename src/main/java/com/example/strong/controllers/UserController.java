package com.example.strong.controllers;

import com.example.strong.models.UserCredentialsModel;
import com.example.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/change-password")
    public String changePassword(@Validated @RequestBody UserCredentialsModel userCredentialsModel) {
        return userService.changePassword(userCredentialsModel);
    }

    @PutMapping("/change-status")
    public String changeStatus(@RequestParam Long id, @RequestParam Boolean isActive) {
        return userService.changeStatus(id, isActive);
    }
}
