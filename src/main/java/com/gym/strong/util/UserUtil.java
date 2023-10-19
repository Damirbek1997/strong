package com.gym.strong.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class UserUtil {
    public static String generateUsername(String firstName, String lastName) {
        return firstName + "." + lastName;
    }

    public static String generatePassword() {
        Random random = new Random();
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            password.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return password.toString();
    }
}
