package com.example.strong.mappers.impl;

import com.example.strong.entities.Training;
import com.example.strong.mappers.AbstractMapper;
import com.example.strong.models.TrainingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper implements AbstractMapper<Training, TrainingModel> {
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public TrainingModel toModel(Training entity) {
        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(entity.getId());
        trainingModel.setTrainingName(entity.getTrainingName());
        trainingModel.setTrainingDate(entity.getTrainingDate());
        trainingModel.setTrainingDuration(entity.getTrainingDuration());
        trainingModel.setTrainerName(entity.getTrainer().getFirstName());

        if (entity.getTrainingType() != null) {
            trainingModel.setTrainingTypeModel(trainingTypeMapper.toModel(entity.getTrainingType()));
        }

        return trainingModel;
    }

    @Override
    public Training toEntity(TrainingModel model) {
        Training trainee = new Training();
        trainee.setId(model.getId());
        trainee.setTrainingName(model.getTrainingName());
        trainee.setTrainingType(trainingTypeMapper.toEntity(model.getTrainingTypeModel()));
        trainee.setTrainingDate(model.getTrainingDate());
        trainee.setTrainingDuration(model.getTrainingDuration());
        return trainee;
    }
}