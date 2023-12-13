package com.example.strong.controllers.impl;

import com.example.strong.controllers.TrainingController;
import com.example.strong.exceptions.BadRequestException;
import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class TrainingControllerImpl implements TrainingController {
    private final TrainingService trainingService;

    @Override
    public List<TrainingModel> getAllByTraineeUsername(String username, Date periodFrom, Date periodTo,
                                                       String trainerName, Long trainingTypeId) {
        return trainingService.getAllByTraineeUsername(username, periodFrom, periodTo, trainerName, trainingTypeId);
    }

    @Override
    public List<TrainingModel> getAllByTrainerUsername(String username, Date periodFrom, Date periodTo, String traineeName) {
        return trainingService.getAllByTrainerUsername(username, periodFrom, periodTo, traineeName);
    }

    @Override
    public void create(CreateTrainingModel createTrainingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Validation error, e: " + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        trainingService.create(createTrainingModel);
    }
}
