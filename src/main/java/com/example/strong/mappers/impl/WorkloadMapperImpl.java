package com.example.strong.mappers.impl;

import com.example.strong.entities.Training;
import com.example.strong.mappers.WorkloadMapper;
import com.example.strong.models.crud.CreateWorkloadModel;
import org.springframework.stereotype.Component;

@Component
public class WorkloadMapperImpl implements WorkloadMapper {
    @Override
    public CreateWorkloadModel toCreateModel(Training training) {
        return CreateWorkloadModel.builder()
                .trainerUsername(training.getTrainer().getUsername())
                .trainerFirstName(training.getTrainer().getFirstName())
                .trainerLastName(training.getTrainer().getLastName())
                .isActive(training.getTrainer().getActive())
                .trainingDate(training.getTrainingDate())
                .trainingDuration(training.getTrainingDuration())
                .build();
    }
}
