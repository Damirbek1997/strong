package com.example.strong.services;

import com.example.strong.enums.SecurityAuthentication;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.UserCredentialsModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;

import java.util.List;

public interface TraineeService {
    List<TraineeModel> getAll(SecurityAuthentication authentication);
    List<TraineeModel> getAllByIds(List<Long> ids, SecurityAuthentication authentication);
    TraineeModel getById(Long id, SecurityAuthentication authentication);
    TraineeModel getByUsername(String username, SecurityAuthentication authentication);
    TraineeModel create(CreateTraineeModel createTraineeModel);
    TraineeModel update(UpdateTraineeModel updateTraineeModel, SecurityAuthentication authentication);
    void deleteById(Long id, SecurityAuthentication authentication);
    void deleteByUsername(String username, SecurityAuthentication authentication);
    void changePassword(UserCredentialsModel userCredentialsModel, SecurityAuthentication authentication);
    void activateById(Long id, SecurityAuthentication authentication);
    void deactivateById(Long id, SecurityAuthentication authentication);
    String authentication(String username, String password);
}
