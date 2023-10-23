package com.example.strong.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCredentialsModel {
    @NotNull
    private Long id;
    @NotNull
    private String newPassword;
}
