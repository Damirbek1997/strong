package com.gym.strong.services.impl;

import com.gym.strong.repository.TraineeDao;
import com.gym.strong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final TraineeDao traineeDao;

    @Autowired
    public UserServiceImpl(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Override
    public String generateUsername(String firstName, String lastName) {
        String username = unionTwoStrings(firstName, lastName);
        int countOfUsers = traineeDao.countByUsername(username);

        if (countOfUsers > 0) {
            return username + countOfUsers;
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
}
