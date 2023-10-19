package com.gym.strong.controllers;

import com.gym.strong.models.TrainingModel;
import com.gym.strong.models.crud.CreateTrainingModel;
import com.gym.strong.services.TrainingService;
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

    @GetMapping("/get-by-id")
    public TrainingModel getById(@RequestParam Long id) {
        return trainingService.getById(id);
    }

    @PostMapping("/create")
    public TrainingModel create(@Validated @RequestBody CreateTrainingModel createTrainingModel) {
        return trainingService.create(createTrainingModel);
    }
}
