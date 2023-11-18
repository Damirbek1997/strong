package com.example.strong.controllers.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.controllers.TrainingController;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingControllerImpl implements TrainingController {
    private final TrainingService trainingService;

    @Override
    @PreAuthenticated
    public List<TrainingModel> getAllByTraineeUsername(String username, Date periodFrom, Date periodTo,
                                                       String trainerName, Long trainingTypeId) {
        return trainingService.getAllByTraineeUsername(username, periodFrom, periodTo, trainerName, trainingTypeId);
    }

    @Override
    @PreAuthenticated
    public List<TrainingModel> getAllByTrainerUsername(String username, Date periodFrom, Date periodTo, String traineeName) {
        return trainingService.getAllByTrainerUsername(username, periodFrom, periodTo, traineeName);
    }

    @Override
    @PreAuthenticated
    public void create(CreateTrainingModel createTrainingModel) {
        trainingService.create(createTrainingModel);
    }
}
