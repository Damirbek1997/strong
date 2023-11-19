package com.example.strong.controllers.impl;

import com.example.strong.controllers.TraineeController;
import com.example.strong.models.response.ResponseCredentialsModel;
import com.example.strong.models.response.ResponseTrainerModel;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TraineeControllerImpl implements TraineeController {
    private final TraineeService traineeService;

    @Override
    public TraineeModel getProfile(String username) {
        return traineeService.getByUsername(username);
    }

    @Override
    public ResponseCredentialsModel create(CreateTraineeModel createTraineeModel) {
        return traineeService.create(createTraineeModel);
    }

    @Override
    public TraineeModel update(Long id, UpdateTraineeModel updateTraineeModel) {
        return traineeService.update(id, updateTraineeModel);
    }

    @Override
    public List<ResponseTrainerModel> updateTrainerList(Long id, List<String> usernames) {
        return traineeService.updateTrainerList(id, usernames);
    }

    @Override
    public void deleteByUsername(String username) {
        traineeService.deleteByUsername(username);
    }
}
