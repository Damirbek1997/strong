package com.example.strong.services;

import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<TrainingModel> getAllByTraineeUsername(String traineeUsername, Date periodFrom, Date periodTo, String trainerName, Long trainingTypeId);
    List<TrainingModel> getAllByTrainerUsername(String trainerUsername, Date periodFrom, Date periodTo, String trainerName);
    TrainingModel create(CreateTrainingModel createTrainingModel);
}
