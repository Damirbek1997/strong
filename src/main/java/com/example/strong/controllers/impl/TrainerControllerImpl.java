package com.example.strong.controllers.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.controllers.TrainerController;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.services.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainerControllerImpl implements TrainerController {
    private final TrainerService trainerService;

    @Override
    @PreAuthenticated
    public ResponseEntity<TrainerModel> getProfile(String username) {
        return new ResponseEntity<>(trainerService.getByUsername(username), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseCredentialsModel> create(CreateTrainerModel createTrainerModel) {
        return new ResponseEntity<>(trainerService.create(createTrainerModel), HttpStatus.CREATED);
    }

    @Override
    @PreAuthenticated
    public ResponseEntity<TrainerModel> update(Long id, UpdateTrainerModel updateTrainerModel) {
        return new ResponseEntity<>(trainerService.update(id, updateTrainerModel), HttpStatus.OK);
    }

    @Override
    @PreAuthenticated
    public ResponseEntity<List<ResponseTrainerModel>> getAllNotBusyTrainers() {
        return new ResponseEntity<>(trainerService.getAllNotBusyTrainers(), HttpStatus.OK);
    }

    @Override
    @PreAuthenticated
    public ResponseEntity<String> updateStatus(String username, Boolean isActive) {
        if (isActive) {
            trainerService.activateByUsername(username);
        } else {
            trainerService.deactivateByUsername(username);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
