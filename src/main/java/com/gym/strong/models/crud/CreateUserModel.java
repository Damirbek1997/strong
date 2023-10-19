package com.gym.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserModel {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
