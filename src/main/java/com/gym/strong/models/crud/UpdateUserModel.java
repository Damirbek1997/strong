package com.gym.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserModel {
    private String firstName;
    private String lastName;
    private Boolean isActive;
}
