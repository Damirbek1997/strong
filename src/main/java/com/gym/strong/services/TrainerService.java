package com.gym.strong.services;

import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTrainerModel;
import com.gym.strong.models.crud.UpdateTrainerModel;

import java.util.List;

public interface TrainerService {
    List<TrainerModel> getAll();
    List<TrainerModel> getAllIn(List<Long> ids);
    TrainerModel getById(Long id);
    TrainerModel create(CreateTrainerModel createTrainerModel);
    TrainerModel update(Long id, UpdateTrainerModel updateTrainerModel);
}
