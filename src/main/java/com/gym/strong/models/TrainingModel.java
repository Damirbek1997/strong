package com.gym.strong.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingModel {
    private Long id;
    private TraineeModel traineeModel;
    private TrainerModel trainerModel;
    private String trainingName;
    private Date trainingDate;
    private Long trainingDuration;
}
