package com.example.strong.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void generateUsername_withValidData_shouldReturnUsername() {
        String firstname = "Ivan";
        String lastname = "Ivanov";

        String actualValue = userService.generateUsername(firstname, lastname);
        String expectedValue = "Ivan.Ivanov";

        assertEquals(actualValue, expectedValue);
    }
}