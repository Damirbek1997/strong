package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTrainerModel extends UpdateUserModel {
    private Long trainingTypeId;
}
