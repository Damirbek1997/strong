package com.example.strong.services.impl;

import com.example.strong.entities.User;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    EncryptionServiceImpl encoder;

    @Test
    void generateUsername_withValidData_shouldReturnUsername() {
        String firstname = "Ivan";
        String lastname = "Ivanov";

        String actualValue = userService.generateUsername(firstname, lastname);
        String expectedValue = "Ivan.Ivanov";

        assertEquals(actualValue, expectedValue);
    }

    @Test
    void changePassword_withValidData_shouldReturnVoid() {
        String username = "Ivan.Ivanov";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User oldUser = new User();
        oldUser.setId(1L);
        oldUser.setFirstName("Ivan");
        oldUser.setLastName("Ivanov");
        oldUser.setUsername("Ivan.Ivanov");
        oldUser.setActive(true);
        oldUser.setPassword(BCrypt.hashpw(oldPassword, BCrypt.gensalt()));

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov");
        user.setActive(true);
        user.setPassword(newPassword);

        when(userRepository.findByUsername(username))
                .thenReturn(oldUser);
        when(encoder.matches(oldPassword, oldUser.getPassword()))
                .thenReturn(true);
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
        user.setActive(true);
        user.setPassword("newPassword");

        when(userRepository.findByUsername(username))
                .thenReturn(null);

        assertThrows(BadRequestException.class, () -> userService.changePassword(username, oldPassword, newPassword));
    }
}