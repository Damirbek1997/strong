package com.example.strong.services;

import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;

import java.util.List;

public interface TrainingService {
    List<TrainingModel> getAll();
    List<TrainingModel> getAllTrainersByUsername(String username);
    List<TrainingModel> getAllTraineesByUsername(String username);
    TrainingModel getById(Long id);
    TrainingModel create(CreateTrainingModel createTrainingModel);
}
