package com.example.strong.controllers;

import com.example.strong.models.TrainerModel;
import com.example.strong.models.crud.CreateTrainerModel;
import com.example.strong.models.crud.UpdateTrainerModel;
import com.example.strong.services.TrainerService;
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

    @GetMapping("/get-all-not-busy-trainers")
    public List<TrainerModel> getAllNotBusyTrainers() {
        return trainerService.getAllNotBusyTrainers();
    }

    @GetMapping("/get-by-id")
    public TrainerModel getById(@RequestParam Long id) {
        return trainerService.getById(id);
    }

    @GetMapping("/get-by-username")
    public TrainerModel getByUsername(@RequestParam String username) {
        return trainerService.getByUsername(username);
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
