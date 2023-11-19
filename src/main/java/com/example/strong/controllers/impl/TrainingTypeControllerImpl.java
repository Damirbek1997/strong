package com.example.strong.controllers.impl;

import com.example.strong.controllers.TrainingTypeController;
import com.example.strong.models.TrainingTypeModel;
import com.example.strong.services.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingTypeControllerImpl implements TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @Override
    public List<TrainingTypeModel> getAll() {
        return trainingTypeService.getAll();
    }
}
