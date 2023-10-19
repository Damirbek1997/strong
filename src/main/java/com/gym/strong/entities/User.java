package com.gym.strong.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
}
