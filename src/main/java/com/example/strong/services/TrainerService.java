package com.example.strong.services;

import com.example.strong.enums.SecurityAuthentication;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;

import java.util.List;

public interface TrainerService {
    List<TrainerModel> getAll(SecurityAuthentication authentication);
    List<TrainerModel> getAllByIds(List<Long> ids, SecurityAuthentication authentication);
    List<TrainerModel> getAllNotBusyTrainers(SecurityAuthentication authentication);
    TrainerModel getById(Long id, SecurityAuthentication authentication);
    TrainerModel getByUsername(String username, SecurityAuthentication authentication);
    TrainerModel create(CreateTrainerModel createTrainerModel);
    TrainerModel update(UpdateTrainerModel updateTrainerModel, SecurityAuthentication authentication);
    void changePassword(UserCredentialsModel userCredentialsModel, SecurityAuthentication authentication);
    void activateById(Long id, SecurityAuthentication authentication);
    void deactivateById(Long id, SecurityAuthentication authentication);
    String authentication(String username, String password);
}
