package com.example.strong.services;

import com.example.strong.entities.Trainer;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;

import java.util.List;

public interface TrainerService {
    List<Trainer> getAllEntitiesByUsernames(List<String> usernames);
    List<ResponseTrainerModel> getAllNotBusyTrainers();
    TrainerModel getByUsername(String username);
    Trainer getEntityByUsername(String username);
    ResponseCredentialsModel create(CreateTrainerModel createTrainerModel);
    TrainerModel update(Long id, UpdateTrainerModel updateTrainerModel);
}
