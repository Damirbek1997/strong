package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.UserService;

import java.util.List;
import java.util.Random;

public class UserServiceImpl implements UserService {
    private final TrainerDao trainerDao;
    private final TraineeDao traineeDao;

    public UserServiceImpl(TrainerDao trainerDao, TraineeDao traineeDao) {
        this.trainerDao = trainerDao;
        this.traineeDao = traineeDao;
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String username = unionTwoStrings(firstName, lastName);

        if (isUsernameBusy(username)) {
            return regenerateUsername(firstName, lastName, 1L);
        }

        return username;
    }

    @Override
    public String generatePassword() {
        Random random = new Random();
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            password.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return password.toString();
    }

    @Override
    public String generateUsername(String firstName, String lastName, String oldFirstName, String oldLastName) {
        String finalFirstName = oldFirstName;
        String finalLastName = oldFirstName;

        if (firstName != null && lastName != null) {
            finalFirstName = firstName;
            finalLastName = lastName;
        }

        if (firstName == null) {
            finalLastName = lastName;
        }

        if (lastName == null) {
            finalFirstName = firstName;
        }

        return generateUsername(finalFirstName, finalLastName);
    }

    private String regenerateUsername(String firstName, String lastName, Long count) {
        String username = unionTwoStrings(firstName, lastName) + count;

        if (isUsernameBusy(username)) {
            username = regenerateUsername(firstName, lastName, count + 1);
        }

        return username;
    }

    private boolean isUsernameBusy(String username) {
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

    private String unionTwoStrings(String firstName, String lastName) {
        return firstName + "." + lastName;
    }
}
