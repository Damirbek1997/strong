package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class CreateTrainingModel {
    @NotNull(message = "Trainee username must be filled")
    private String traineeUsername;
    @NotNull(message = "Trainer username must be filled")
    private String trainerUsername;
    @NotNull(message = "Training name must be filled")
    private String trainingName;
    @NotNull(message = "Training date must be filled")
    private Date trainingDate;
    @NotNull(message = "Training duration must be filled")
    private Long trainingDuration;
}
