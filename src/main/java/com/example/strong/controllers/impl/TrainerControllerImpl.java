package com.example.strong.controllers.impl;

import com.example.strong.configs.annotations.PreAuthenticated;
import com.example.strong.controllers.TrainerController;
import com.example.strong.models.ResponseCredentialsModel;
import com.example.strong.models.ResponseTrainerModel;
import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.services.TrainerService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainerControllerImpl implements TrainerController {
    private final TrainerService trainerService;

    @Override
    @PreAuthenticated
    public TrainerModel getProfile(String username) {
        return trainerService.getByUsername(username);
    }

    @Override
    @Timed(value = "trainer.creating.time", description = "Time taken to create trainer")
    public ResponseCredentialsModel create(CreateTrainerModel createTrainerModel) {
        return trainerService.create(createTrainerModel);
    }

    @Override
    @PreAuthenticated
    public TrainerModel update(Long id, UpdateTrainerModel updateTrainerModel) {
        return trainerService.update(id, updateTrainerModel);
    }

    @Override
    @PreAuthenticated
    public List<ResponseTrainerModel> getAllNotBusyTrainers() {
        return trainerService.getAllNotBusyTrainers();
    }
}
