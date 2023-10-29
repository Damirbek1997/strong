package com.gym.strong.services.impl;

import com.gym.strong.entities.Trainee;
import com.gym.strong.entities.Trainer;
import com.gym.strong.repository.TraineeDao;
import com.gym.strong.repository.TrainerDao;
import com.gym.strong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final TraineeDao traineeDao;
    private final TrainerDao trainerDao;

    @Autowired
    public UserServiceImpl(TraineeDao traineeDao, TrainerDao trainerDao) {
        this.traineeDao = traineeDao;
        this.trainerDao = trainerDao;
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String username = unionTwoStrings(firstName, lastName);
        int counter = countByUsername(username);

        if (counter > 0) {
            return username + counter;
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

    private String unionTwoStrings(String firstName, String lastName) {
        return firstName + "." + lastName;
    }

    public Integer countByUsername(String username) {
        List<Trainee> traineeList = traineeDao.getAll();
        List<Trainer> trainerList = trainerDao.getAll();

        String usernameWithoutDigits = removeAllDigit(username);
        int counter = 0;

        for (Trainee trainee : traineeList) {
            if (trainee.getUsername().startsWith(usernameWithoutDigits)) {
                counter++;
            }
        }

        for (Trainer trainer : trainerList) {
            if (trainer.getUsername().startsWith(usernameWithoutDigits)) {
                counter++;
            }
        }

        return counter;
    }

    private String removeAllDigit(String str) {
        char[] charArray = str.toCharArray();
        StringBuilder result = new StringBuilder();

        for (char c : charArray) {
            if (!Character.isDigit(c)) {
                result.append(c);
            }
        }

        return result.toString();
    }
}
