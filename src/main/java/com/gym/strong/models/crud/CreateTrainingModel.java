package com.gym.strong.models.crud;

import com.gym.strong.models.TrainingTypeModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainingModel {
    @NotNull
    private Long traineeId;
    @NotNull
    private Long trainerId;
    @NotNull
    private String trainingName;
    @NotNull
    private TrainingTypeModel trainingTypeModel;
    @NotNull
    private Date trainingDate;
    @NotNull
    private Long trainingDuration;
}
