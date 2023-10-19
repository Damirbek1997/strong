package com.gym.strong.controllers;

import com.gym.strong.models.TrainerModel;
import com.gym.strong.models.crud.CreateTrainerModel;
import com.gym.strong.models.crud.UpdateTrainerModel;
import com.gym.strong.services.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping("/get-all")
    public List<TrainerModel> getAll() {
        return trainerService.getAll();
    }

    @GetMapping("/get-by-id")
    public TrainerModel getById(@RequestParam Long id) {
        return trainerService.getById(id);
    }

    @PostMapping("/create")
    public TrainerModel create(@Validated @RequestBody CreateTrainerModel createTrainerModel) {
        return trainerService.create(createTrainerModel);
    }

    @PutMapping("/update")
    public TrainerModel update(@RequestBody @Validated UpdateTrainerModel updateTrainerModel) {
        return trainerService.update(updateTrainerModel);
    }
}
