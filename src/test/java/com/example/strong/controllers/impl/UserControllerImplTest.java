package com.example.strong.controllers.impl;

import com.example.strong.models.response.ResponseAuthenticationModel;
import com.example.strong.services.BruteForceProtectService;
import com.example.strong.services.JwtService;
import com.example.strong.services.UserService;
import com.example.strong.services.impl.CustomUserDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserControllerImpl.class)
class UserControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private BruteForceProtectService protectService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private CustomUserDetailsService userDetailsService;

    private String username;
    private String contentType;

    @BeforeEach
    void beforeAll() {
        username = "Ivan.Ivanov";
        contentType = "application/json";
    }

    @AfterEach
    void afterEach() {
        username = null;
        contentType = null;
    }

    @Test
    @WithMockUser
    void login_withValidData_shouldReturnResponseAuthenticationModel() throws Exception {
        ResponseAuthenticationModel responseAuthenticationModel = new ResponseAuthenticationModel();
        responseAuthenticationModel.setUsername(username);
        responseAuthenticationModel.setToken("token");

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new User(username, "password", new ArrayList<>()),
                "password",
                new ArrayList<>()
        );
        UserDetails userDetails = new User("username", "password", new ArrayList<>());

        when(protectService.isBlocked(username))
                .thenReturn(false);
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken))
                .thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(username))
                .thenReturn(userDetails);
        when(jwtService.generateToken(userDetails))
                .thenReturn("token");

        mockMvc.perform(get("/user/login")
                        .param("username", username)
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.username").value(responseAuthenticationModel.getUsername()))
                .andExpect(jsonPath("$.token").value(responseAuthenticationModel.getToken()));
    }

    @Test
    @WithMockUser
    void changeCredentials_withValidData_shouldReturnVoid() throws Exception {
        mockAuthorization();
        doNothing()
                .when(userService)
                .changePassword(username, "123123", "321321");

        mockMvc.perform(put("/user/change-credentials")
                        .header("Authorization", "Bearer token")
                        .with(csrf())
                        .param("username", username)
                        .param("oldPassword", "123123")
                        .param("newPassword", "321321"))
                .andExpect(status().isOk());

        verify(userService)
                .changePassword(username, "123123", "321321");
    }

    @Test
    @WithMockUser
    void updateStatus_withTrueArgument_shouldReturnOk() throws Exception {
        mockAuthorization();
        doNothing()
                .when(userService)
                .activateByUsername(username);

        mockMvc.perform(patch("/user/status")
                        .header("Authorization", "Bearer token")
                        .with(csrf())
                        .param("username", username)
                        .param("active", "true"))
                .andExpect(status().isOk());

        verify(userService)
                .activateByUsername(username);
    }

    @Test
    @WithMockUser
    void updateStatus_withFalseArgument_shouldReturnOk() throws Exception {
        mockAuthorization();
        doNothing()
                .when(userService)
                .deactivateByUsername(username);

        mockMvc.perform(patch("/user/status")
                        .header("Authorization", "Bearer token")
                        .with(csrf())
                        .param("username", username)
                        .param("active", "false"))
                .andExpect(status().isOk());

        verify(userService)
                .deactivateByUsername(username);
    }

    private void mockAuthorization() {
        UserDetails userDetails = new User("username", "password", new ArrayList<>());

        when(jwtService.validateToken("token"))
                .thenReturn(true);
        when(jwtService.extractUsername("token"))
                .thenReturn(username);
        when(userDetailsService.loadUserByUsername(username))
                .thenReturn(userDetails);
    }
}