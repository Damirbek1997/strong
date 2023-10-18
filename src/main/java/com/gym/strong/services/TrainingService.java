package com.gym.strong.services;

import com.gym.strong.models.TrainingModel;
import com.gym.strong.models.crud.CreateTrainingModel;

import java.util.List;

public interface TrainingService {
    List<TrainingModel> getAll();
    TrainingModel getById(Long id);
    TrainingModel create(CreateTrainingModel createTrainingModel);
}
