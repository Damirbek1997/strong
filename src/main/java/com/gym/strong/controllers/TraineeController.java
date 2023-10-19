package com.gym.strong.controllers;

import com.gym.strong.models.TraineeModel;
import com.gym.strong.models.crud.CreateTraineeModel;
import com.gym.strong.models.crud.UpdateTraineeModel;
import com.gym.strong.services.TraineeService;
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

    @GetMapping("/get-by-id")
    public TraineeModel getById(@RequestParam Long id) {
        return traineeService.getById(id);
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
}
