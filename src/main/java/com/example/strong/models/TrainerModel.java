package com.example.strong.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainerModel extends UserModel {
    private Long trainingTypeId;
    private List<ResponseTraineeModel> traineeModels;
}
