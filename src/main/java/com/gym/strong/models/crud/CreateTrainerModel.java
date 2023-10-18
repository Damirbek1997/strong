package com.gym.strong.models.crud;

import com.gym.strong.models.TrainingTypeModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainerModel {
    @NotNull
    private CreateUserModel createUserModel;
    @NotNull
    private TrainingTypeModel trainingTypeModel;
    private List<Long> traineeIds;
}
