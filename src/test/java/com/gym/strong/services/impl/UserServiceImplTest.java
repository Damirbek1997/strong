package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.repository.TrainerDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    TraineeDao traineeDao;
    @Mock
    TrainerDao trainerDao;

    @Test
    void generateUsername_shouldReturnUniqueUsername() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String expectedUsername = firstName + "." + lastName;
        String actualValue = userService.generateUsername(firstName, lastName);

        assertEquals(expectedUsername, actualValue);
    }

    @Test
    void generateUsername_withNotUniqueUsername_shouldReturnUniqueUsername() {
        Date birthDate = new Date();

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("Ivan");
        trainee.setLastName("Ivanov");
        trainee.setUsername("Ivan.Ivanov");
        trainee.setIsActive(true);
        trainee.setBirthday(birthDate);
        trainee.setAddress("Moscow");

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Ivan");
        trainer.setLastName("Ivanov");
        trainer.setUsername("Ivan.Ivanov1");
        trainer.setIsActive(true);

        String firstName = "Ivan";
        String lastName = "Ivanov";
        String expectedUsername = firstName + "." + lastName + 2;

        when(traineeDao.getAll()).thenReturn(Collections.singletonList(trainee));
        when(trainerDao.getAll()).thenReturn(Collections.singletonList(trainer));

        String actualValue = userService.generateUsername(firstName, lastName);
        assertEquals(expectedUsername, actualValue);
    }
}
