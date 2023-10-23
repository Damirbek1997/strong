package com.example.strong.models.crud;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserModel {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
