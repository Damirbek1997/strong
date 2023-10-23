package com.example.strong.controllers;

import com.example.strong.models.TraineeModel;
import com.example.strong.models.crud.CreateTraineeModel;
import com.example.strong.models.crud.UpdateTraineeModel;
import com.example.strong.services.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    @GetMapping("/get-all")
    public List<TraineeModel> getAll() {
        return traineeService.getAll();
    }

    @GetMapping("/get-all-by-in-ids")
    public List<TraineeModel> getAllByIn(@RequestParam List<Long> ids) {
        return traineeService.getAllByIn(ids);
    }

    @GetMapping("/get-by-id")
    public TraineeModel getById(@RequestParam Long id) {
        return traineeService.getById(id);
    }

    @GetMapping("/get-by-username")
    public TraineeModel getByUsername(@RequestParam String username) {
        return traineeService.getByUsername(username);
    }

    @PostMapping("/create")
    public TraineeModel create(@Validated @RequestBody CreateTraineeModel createTraineeModel) {
        return traineeService.create(createTraineeModel);
    }

    @PutMapping("/update")
    public TraineeModel update(@RequestBody @Validated UpdateTraineeModel updateTraineeModel) {
        return traineeService.update(updateTraineeModel);
    }

    @DeleteMapping("/delete-by-id")
    public void deleteById(@RequestParam Long id) {
        traineeService.deleteById(id);
    }

    @DeleteMapping("/delete-by-username")
    public void deleteByUsername(@RequestParam String username) {
        traineeService.deleteByUsername(username);
    }
}
