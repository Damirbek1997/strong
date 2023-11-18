package com.example.strong.services;

import com.example.strong.entities.Trainee;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;

import java.util.List;

public interface TraineeService {
    TraineeModel getByUsername(String username);
    Trainee getEntityByUsername(String username);
    ResponseCredentialsModel create(CreateTraineeModel createTraineeModel);
    TraineeModel update(Long id, UpdateTraineeModel updateTraineeModel);
    List<ResponseTrainerModel> updateTrainerList(Long id, List<String> usernames);
    void deleteById(Long id);
}
