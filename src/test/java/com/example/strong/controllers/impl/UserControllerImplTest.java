package com.example.strong.controllers.impl;

import com.example.strong.services.impl.UserServiceImpl;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {
    @InjectMocks
    private UserControllerImpl userController;
    @Mock
    UserServiceImpl userService;

    private String username;

    @BeforeEach
    void beforeAll() {
        RestAssuredMockMvc.standaloneSetup(userController);
        username = "Ivan.Ivanov";
    }

    @AfterEach
    void afterEach() {
        username = null;
    }

    @Test
    void login() {
        RestAssuredMockMvc.given()
                .when()
                .get("/user/login")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Test
    void changeCredentials() {
        doNothing()
                .when(userService)
                .changePassword(username, "123123", "321321");

        RestAssuredMockMvc.given()
                .header("username", username)
                .header("password", 123123)
                .param("newPassword", 321321)
                .when()
                .put("/user/change-credentials")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value());

        verify(userService)
                .changePassword(username, "123123", "321321");
    }

    @Test
    void updateStatus_withTrueArgument_shouldReturnOk() {
        doNothing()
                .when(userService)
                .activateByUsername(username);

        RestAssuredMockMvc.given()
                .header("username", username)
                .param("active", true)
                .when()
                .patch("/user/status")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value());

        verify(userService)
                .activateByUsername(username);
    }

    @Test
    void updateStatus_withFalseArgument_shouldReturnOk() {
        doNothing()
                .when(userService)
                .deactivateByUsername(username);

        RestAssuredMockMvc.given()
                .header("username", username)
                .param("active", false)
                .when()
                .patch("/user/status")
                .then()
                .log().all().assertThat().statusCode(HttpStatus.OK.value());

        verify(userService)
                .deactivateByUsername(username);
    }
}