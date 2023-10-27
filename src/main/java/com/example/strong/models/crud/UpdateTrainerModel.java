package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateTrainerModel extends UpdateUserModel {
    @NotNull
    private Long id;
    private Long trainingTypeId;
}
