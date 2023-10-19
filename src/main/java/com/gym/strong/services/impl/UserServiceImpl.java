package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final TrainerDao trainerDao;
    private final TraineeDao traineeDao;

    @Override
    public String regenerateUsername(String firstName, String lastName, Long count) {
        String username = firstName + "." + lastName + count;

        if (isUsernameBusy(username)) {
            count++;
            regenerateUsername(firstName, lastName, count);
        }

        return username;
    }

    @Override
    public String regenerateUsername(String firstName, String lastName, String oldFirstName, String oldLastName) {
        if (firstName != null && lastName != null) {
            String username = firstName + "." + lastName;

            if (isUsernameBusy(username)) {
                return regenerateUsername(firstName, lastName, 1L);
            }

            return username;
        }

        if (firstName == null) {
            String username = oldFirstName + "." + lastName;

            if (isUsernameBusy(username)) {
                return regenerateUsername(oldFirstName, lastName, 1L);
            }

            return username;
        }

        if (lastName == null) {
            String username = firstName + "." + oldLastName;

            if (isUsernameBusy(username)) {
                return regenerateUsername(firstName, oldLastName, 1L);
            }

            return username;
        }

        return null;
    }

    public boolean isUsernameBusy(String username) {
        List<Trainee> traineeList = traineeDao.getAll();
        List<Trainer> trainerList = trainerDao.getAll();

        for (Trainee trainee : traineeList) {
            if (trainee.getUsername().equals(username)) {
                return true;
            }
        }

        for (Trainer trainer : trainerList) {
            if (trainer.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }
}
