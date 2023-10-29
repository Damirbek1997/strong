package com.example.strong.services;

import com.example.strong.enums.SecurityAuthentication;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;

import java.util.List;

public interface TrainingService {
    List<TrainingModel> getAll(SecurityAuthentication authentication);
    List<TrainingModel> getAllTrainersByUsername(String username, SecurityAuthentication authentication);
    List<TrainingModel> getAllTraineesByUsername(String username, SecurityAuthentication authentication);
    TrainingModel getById(Long id, SecurityAuthentication authentication);
    TrainingModel create(CreateTrainingModel createTrainingModel, SecurityAuthentication authentication);
}
