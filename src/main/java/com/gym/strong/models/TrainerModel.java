package com.gym.strong.models;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerModel {
    private Long id;
    private UserModel userModel;
    private Date birthday;
    private String address;
    private TrainingTypeModel trainingTypeModel;
    private Set<TraineeModel> traineeModels;
}
