package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateUserModel {
    @NotNull(message = "First name must be filled!")
    private String firstName;
    @NotNull(message = "Last name must be filled!")
    private String lastName;
    @NotNull(message = "Active must be filled!")
    private Boolean active;
}
