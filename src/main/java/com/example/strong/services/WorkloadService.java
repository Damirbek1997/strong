package com.example.strong.services;

import com.example.strong.entities.Training;
import com.example.strong.enums.WorkloadActionType;

import java.util.List;

public interface WorkloadService {
    void create(Training training, WorkloadActionType workloadActionType);
    void create(List<Training> trainings, WorkloadActionType workloadActionType);
}
