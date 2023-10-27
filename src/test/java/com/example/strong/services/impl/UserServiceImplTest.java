package com.example.strong.services.impl;

import com.example.strong.entities.User;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateUserModel;
import com.example.strong.models.crud.UpdateUserModel;
import com.example.strong.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @Test
    void create_withValidData_shouldReturnUser() {
        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setFirstName("Ivan");
        createUserModel.setLastName("Ivanov");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("Ivan.Ivanov1");
        user.setIsActive(true);

        when(userRepository.countByUsernameLike("Ivan.Ivanov"))
                .thenReturn(1L);
        when(userRepository.save(any()))
                .thenReturn(user);

        User response = userService.create(createUserModel);
        assertEquals(user, response);
    }

    @Test
    void update_withFirstName_shouldReturnUser() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setId(1L);
        updateUserModel.setFirstName("Petya");

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setFirstName("Ivan");
        currentUser.setLastName("Ivanov");
        currentUser.setUsername("Ivan.Ivanov");
        currentUser.setIsActive(true);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Petya");
        user.setLastName("Ivanov");
        user.setUsername("Petya.Ivanov");
        user.setIsActive(true);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.countByUsernameLike("Petya.Ivanov"))
                .thenReturn(0L);
        when(userRepository.save(any()))
                .thenReturn(user);

        User response = userService.update(updateUserModel);
        assertEquals(user, response);
    }

    @Test
    void changePassword_withValidData_shouldReturnVoid() {
        UserCredentialsModel userCredentialsModel = new UserCredentialsModel();
        userCredentialsModel.setId(1L);
        userCredentialsModel.setNewPassword("testpassword");

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setFirstName("Ivan");
        currentUser.setLastName("Ivanov");
        currentUser.setUsername("Ivan.Ivanov");
        currentUser.setIsActive(true);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Petya");
        user.setLastName("Ivanov");
        user.setUsername("Petya.Ivanov");
        user.setPassword("testpassword");
        user.setIsActive(true);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(currentUser));
        when(userRepository.save(any()))
                .thenReturn(user);

        userService.changePassword(userCredentialsModel);
        assertEquals(userCredentialsModel.getNewPassword(), user.getPassword());
    }

    @Test
    void activateById_withValidData_shouldReturnVoid() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Petya");
        user.setLastName("Ivanov");
        user.setUsername("Petya.Ivanov");
        user.setIsActive(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(userRepository.save(any()))
                .thenReturn(user);

        userService.activateById(1L);
        assertEquals(true, user.getIsActive());
    }

    @Test
    void deactivateById_withValidData_shouldReturnVoid() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Petya");
        user.setLastName("Ivanov");
        user.setUsername("Petya.Ivanov");
        user.setIsActive(true);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(userRepository.save(any()))
                .thenReturn(user);

        userService.deactivateById(1L);
        assertEquals(false, user.getIsActive());
    }
}