package com.example.strong.services;

import com.example.strong.entities.TrainingType;
import com.example.strong.models.TrainingTypeModel;

import java.util.List;

public interface TrainingTypeService {
    List<TrainingTypeModel> getAll();
    TrainingType getById(Long id);
}
