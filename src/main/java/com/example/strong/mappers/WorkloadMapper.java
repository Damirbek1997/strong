package com.example.strong.mappers;

import com.example.strong.entities.Training;
import com.example.strong.models.crud.CreateWorkloadModel;

public interface WorkloadMapper {
    CreateWorkloadModel toCreateModel(Training training);
}
