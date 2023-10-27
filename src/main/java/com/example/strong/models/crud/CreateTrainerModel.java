package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateTrainerModel extends CreateUserModel {
    @NotNull
    private Long trainingTypeId;
}
