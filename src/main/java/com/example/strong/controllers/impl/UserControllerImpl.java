package com.example.strong.controllers.impl;

import com.example.strong.controllers.UserController;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.models.response.ResponseAuthenticationModel;
import com.example.strong.services.BruteForceProtectService;
import com.example.strong.services.JwtService;
import com.example.strong.services.UserService;
import com.example.strong.services.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final BruteForceProtectService protectService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseAuthenticationModel login(String username, String password) {
        if (protectService.isBlocked(username)) {
            throw new BadRequestException("User is blocked for 5 min");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authentication.getName());
        String jwt = jwtService.generateToken(userDetails);

        return new ResponseAuthenticationModel(username, jwt);
    }

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
