package com.example.strong.services.impl;

import com.example.strong.clients.workload.WorkloadServiceClient;
import com.example.strong.entities.Training;
import com.example.strong.enums.WorkloadActionType;
import com.example.strong.models.crud.CreateWorkloadModel;
import com.example.strong.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadServiceImpl implements WorkloadService {
    private final WorkloadServiceClient workloadServiceClient;

    @Override
    public void create(Training training, WorkloadActionType workloadActionType) {
        CreateWorkloadModel createWorkloadModel = toCreateWorkloadModel(training, workloadActionType);
        workloadServiceClient.create(createWorkloadModel);
        log.debug("Created a workload with a model {}", createWorkloadModel);
    }

    @Override
    public void create(List<Training> trainings, WorkloadActionType workloadActionType) {
        List<CreateWorkloadModel> createWorkloadModels = new ArrayList<>();

        for (Training training : trainings) {
            createWorkloadModels.add(toCreateWorkloadModel(training, workloadActionType));
        }

        workloadServiceClient.create(createWorkloadModels);
        log.debug("Created workloads with models {}", createWorkloadModels);
    }

    private CreateWorkloadModel toCreateWorkloadModel(Training training, WorkloadActionType workloadActionType) {
        return CreateWorkloadModel.builder()
                .trainerUsername(training.getTrainer().getUsername())
                .trainerFirstName(training.getTrainer().getFirstName())
                .trainerLastName(training.getTrainer().getLastName())
                .isActive(training.getTrainer().getActive())
                .trainingDate(training.getTrainingDate())
                .trainingDuration(training.getTrainingDuration())
                .actionType(workloadActionType)
                .build();
    }
}
