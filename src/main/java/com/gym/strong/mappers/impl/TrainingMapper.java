package com.gym.strong.mappers.impl;

import com.gym.strong.entities.Training;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.TrainingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper implements AbstractMapper<Training, TrainingModel> {
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;

    @Override
    public TrainingModel toModel(Training entity) {
        TrainingModel trainingModel = new TrainingModel();
        trainingModel.setId(entity.getId());
        trainingModel.setTraineeModel(traineeMapper.toModel(entity.getTrainee()));
        trainingModel.setTrainerModel(trainerMapper.toModel(entity.getTrainer()));
        trainingModel.setTrainingName(entity.getTrainingName());
        trainingModel.setTrainingDate(entity.getTrainingDate());
        trainingModel.setTrainingDuration(entity.getTrainingDuration());
        return trainingModel;
    }

    @Override
    public Training toEntity(TrainingModel model) {
        Training trainee = new Training();
        trainee.setId(model.getId());
        trainee.setTrainee(traineeMapper.toEntity(model.getTraineeModel()));
        trainee.setTrainer(trainerMapper.toEntity(model.getTrainerModel()));
        trainee.setTrainingName(model.getTrainingName());
        trainee.setTrainingDate(model.getTrainingDate());
        trainee.setTrainingDuration(model.getTrainingDuration());
        return trainee;
    }
}
