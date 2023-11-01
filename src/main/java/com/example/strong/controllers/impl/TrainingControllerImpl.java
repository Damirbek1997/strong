package com.example.strong.controllers.impl;

import com.example.strong.controllers.TrainingController;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingControllerImpl implements TrainingController {
    private final TrainingService trainingService;

    @Override
    public ResponseEntity<List<TrainingModel>> getAllByTraineeUsername(String username, Date periodFrom, Date periodTo,
                                                                       String trainerName, Long trainingTypeId) {
        return new ResponseEntity<>(trainingService.getAllByTraineeUsername(username, periodFrom, periodTo, trainerName, trainingTypeId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainingModel>> getAllByTrainerUsername(String username, Date periodFrom, Date periodTo, String traineeName) {
        return new ResponseEntity<>(trainingService.getAllByTrainerUsername(username, periodFrom, periodTo, traineeName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> create(CreateTrainingModel createTrainingModel) {
        trainingService.create(createTrainingModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
