package com.gym.strong.services.impl;

import com.gym.strong.repository.TraineeDao;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final TrainerDao trainerDao;
    private final TraineeDao traineeDao;

    @Override
    public String regenerateUsername(String firstName, String lastName, Long count) {
        String username = firstName + "." + lastName + count;

        if (trainerDao.isTrainerExistWith(username) || traineeDao.isTraineeExistWith(username)) {
            count++;
            regenerateUsername(firstName, lastName, count);
        }

        return username;
    }

    @Override
    public String regenerateUsername(String firstName, String lastName, String oldFirstName, String oldLastName) {
        if (firstName != null && lastName != null) {
            String username = firstName + "." + lastName;

            if (trainerDao.isTrainerExistWith(username) || traineeDao.isTraineeExistWith(username)) {
                return regenerateUsername(firstName, lastName, 1L);
            }

            return username;
        }

        if (firstName == null) {
            String username = oldFirstName + "." + lastName;

            if (trainerDao.isTrainerExistWith(username) || traineeDao.isTraineeExistWith(username)) {
                return regenerateUsername(oldFirstName, lastName, 1L);
            }

            return username;
        }

        if (lastName == null) {
            String username = firstName + "." + oldLastName;

            if (trainerDao.isTrainerExistWith(username) || traineeDao.isTraineeExistWith(username)) {
                return regenerateUsername(firstName, oldLastName, 1L);
            }

            return username;
        }

        return null;
    }
}
