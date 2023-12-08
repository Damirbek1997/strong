package com.example.strong.controllers.impl;

import com.example.strong.models.response.ResponseAuthorizationModel;
import com.example.strong.services.AuthService;
import com.example.strong.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerImpl.class)
class UserControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;

    private String username;

    @BeforeEach
    void beforeAll() {
        username = "Ivan.Ivanov";
    }

    @AfterEach
    void afterEach() {
        username = null;
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
        ResponseAuthorizationModel responseAuthorizationModel = new ResponseAuthorizationModel();
        responseAuthorizationModel.setUsername(username);
        responseAuthorizationModel.setAuthorities("[USER]");

        when(authService.getAuthorities("token"))
                .thenReturn(responseAuthorizationModel);
    }
}