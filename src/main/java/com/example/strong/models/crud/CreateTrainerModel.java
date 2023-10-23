package com.example.strong.models.crud;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainerModel {
    @NotNull
    private CreateUserModel createUserModel;
    @NotNull
    private Long trainingTypeId;
}
