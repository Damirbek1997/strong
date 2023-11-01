package com.example.strong.services.impl;

import com.example.strong.entities.User;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @Test
    void generateUsername_withValidData_shouldReturnUsername() {
        String firstname = "Ivan";
        String lastname = "Ivanov";

        String actualValue = userService.generateUsername(firstname, lastname);
        String expectedValue = "Ivan.Ivanov";

        assertEquals(actualValue, expectedValue);
    }

    @Test
    void isUserExist_withValidData_shouldReturnTrue() {
        User user = new User();
        user.setUsername("Ivan");
        user.setPassword("Ivan");

        String username = "Ivan";
        String password = "Ivan";

        when(userRepository.findByUsernameAndPassword(username, password))
                .thenReturn(user);

        boolean userExist = userService.isUserExist(username, password);

        assertTrue(userExist);
    }

    @Test
    void isUserExist_withValidData_shouldReturnFalse() {
        String username = "Ivan";
        String password = "Ivan";

        when(userRepository.findByUsernameAndPassword(username, password))
                .thenReturn(null);

        boolean userExist = userService.isUserExist(username, password);

        assertFalse(userExist);
    }

    @Test
    void changePassword_withValidData_shouldReturnVoid() {
        String username = "Ivan.Ivanov";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);
        user.setPassword("newPassword");

        when(userRepository.findByUsernameAndPassword(username, oldPassword))
                .thenReturn(user);
        when(userRepository.save(any()))
                .thenReturn(user);

        userService.changePassword(username, oldPassword, newPassword);
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void changePassword_withValidData_shouldReturnException() {
        String username = "Ivan.Ivanov";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setIsActive(true);
        user.setPassword("newPassword");

        when(userRepository.findByUsernameAndPassword(username, oldPassword))
                .thenReturn(null);

        assertThrows(BadRequestException.class, () -> userService.changePassword(username, oldPassword, newPassword));
    }
}