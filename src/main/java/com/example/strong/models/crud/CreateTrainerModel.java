package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTrainerModel extends CreateUserModel {
    private Long trainingTypeId;
}
