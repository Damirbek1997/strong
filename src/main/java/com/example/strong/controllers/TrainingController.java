package com.example.strong.controllers;

import com.example.strong.models.TrainingModel;
import com.example.strong.models.crud.CreateTrainingModel;
import com.example.strong.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/training")
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping("/get-all")
    public List<TrainingModel> getAll() {
        return trainingService.getAll();
    }

    @GetMapping("/get-all-trainers-by-username")
    public List<TrainingModel> getAllTrainersByUsername(@RequestParam String username) {
        return trainingService.getAllTrainersByUsername(username);
    }

    @GetMapping("/get-all-trainees-by-username")
    public List<TrainingModel> getAllTraineesByUsername(@RequestParam String username) {
        return trainingService.getAllTraineesByUsername(username);
    }

    @GetMapping("/get-by-id")
    public TrainingModel getById(@RequestParam Long id) {
        return trainingService.getById(id);
    }

    @PostMapping("/create")
    public TrainingModel create(@Validated @RequestBody CreateTrainingModel createTrainingModel) {
        return trainingService.create(createTrainingModel);
    }
}
