package com.example.strong.services;

import com.example.strong.models.TrainerModel;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;

import java.util.List;

public interface TrainerService {
    List<TrainerModel> getAll();
    List<TrainerModel> getAllByIds(List<Long> ids);
    List<TrainerModel> getAllNotBusyTrainers();
    TrainerModel getById(Long id);
    TrainerModel getByUsername(String username);
    TrainerModel create(CreateTrainerModel createTrainerModel);
    TrainerModel update(UpdateTrainerModel updateTrainerModel);
    void changePassword(UserCredentialsModel userCredentialsModel);
    void activateById(Long id);
    void deactivateById(Long id);
}
