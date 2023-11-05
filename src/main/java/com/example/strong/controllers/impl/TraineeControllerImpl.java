package com.example.strong.controllers.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.controllers.TraineeController;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TraineeControllerImpl implements TraineeController {
    private final TraineeService traineeService;

    @Override
    @PreAuthenticated
    public ResponseEntity<TraineeModel> getProfile(String username) {
        return new ResponseEntity<>(traineeService.getByUsername(username), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseCredentialsModel> create(CreateTraineeModel createTraineeModel) {
        return new ResponseEntity<>(traineeService.create(createTraineeModel), HttpStatus.CREATED);
    }

    @Override
    @PreAuthenticated
    public ResponseEntity<TraineeModel> update(Long id, UpdateTraineeModel updateTraineeModel) {
        return new ResponseEntity<>(traineeService.update(id, updateTraineeModel), HttpStatus.OK);
    }

    @Override
    @PreAuthenticated
    public ResponseEntity<List<ResponseTrainerModel>> updateTrainerList(Long id, List<String> usernames) {
        return new ResponseEntity<>(traineeService.updateTrainerList(id, usernames), HttpStatus.OK);
    }

    @Override
    @PreAuthenticated
    public ResponseEntity<String> deleteByUsername(String username) {
        traineeService.deleteById(traineeService.getByUsername(username).getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
