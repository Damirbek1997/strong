package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserModel {
    @NotNull(message = "First name must be filled!")
    private String firstName;
    @NotNull(message = "Last name must be filled!")
    private String lastName;
}
