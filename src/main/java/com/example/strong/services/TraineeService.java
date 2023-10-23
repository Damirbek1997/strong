package com.example.strong.services;

import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;

import java.util.List;

public interface TraineeService {
    List<TraineeModel> getAll();
    List<TraineeModel> getAllByIn(List<Long> ids);
    TraineeModel getById(Long id);
    TraineeModel getByUsername(String username);
    TraineeModel create(CreateTraineeModel createTraineeModel);
    TraineeModel update(UpdateTraineeModel updateTraineeModel);
    void deleteById(Long id);
    void deleteByUsername(String username);
}
