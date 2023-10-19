package com.gym.strong.services;

import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.models.crud.UpdateTraineeModel;

import java.util.List;

public interface TraineeService {
    List<TraineeModel> getAll();
    List<TraineeModel> getAll(List<Long> ids);
    TraineeModel getById(Long id);
    TraineeModel create(CreateTraineeModel createTraineeModel);
    TraineeModel update(UpdateTraineeModel updateTraineeModel);
    void deleteById(Long id);
}
