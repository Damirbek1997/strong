package com.example.strong.models.crud;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserModel {
    @NotNull
    private Long id;
    private String firstName;
    private String lastName;
}
