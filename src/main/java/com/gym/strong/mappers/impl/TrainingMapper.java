package com.gym.strong.mappers.impl;

import com.gym.strong.entities.Training;
import com.gym.strong.exceptions.InsertStorageFromFileException;
import com.gym.strong.mappers.AbstractMapper;
import com.gym.strong.models.TrainingModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TrainingMapper implements AbstractMapper<Training, TrainingModel> {
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;

    public TrainingMapper(TraineeMapper traineeMapper, TrainerMapper trainerMapper) {
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
    }

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

    public Training fromString(String line) throws ParseException {
        String[] parts = line.split(",");

        if (parts.length >= 4) {
            Training training = new Training();
            training.setTrainee(traineeMapper.fromString(parts[1]));
            training.setTrainer(trainerMapper.fromString(parts[2]));
            training.setTrainingName(parts[3]);
            training.setTrainingDate(new SimpleDateFormat("yyyy-MM-dd").parse(parts[4]));
            training.setTrainingDuration(Long.valueOf(parts[5]));

            return training;
        }

        throw new InsertStorageFromFileException("Error while parsing string to entity: all fields of User must be filled!");
    }
}
