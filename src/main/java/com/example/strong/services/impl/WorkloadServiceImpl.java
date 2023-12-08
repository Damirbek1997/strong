package com.example.strong.services.impl;

import com.example.strong.entities.Training;
import com.example.strong.enums.WorkloadActionType;
import com.example.strong.models.crud.CreateWorkloadModel;
import com.example.strong.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadServiceImpl implements WorkloadService {
    private final JmsTemplate jmsTemplate;

    @Override
    public void create(Training training, WorkloadActionType workloadActionType) {
        CreateWorkloadModel createWorkloadModel = toCreateWorkloadModel(training, workloadActionType);
        jmsTemplate.convertAndSend("workload-create", createWorkloadModel);
        log.info("Sent a workload with a model {} to Workload service", createWorkloadModel);
    }

    @Override
    public void create(List<Training> trainings, WorkloadActionType workloadActionType) {
        for (Training training : trainings) {
            create(training, workloadActionType);
        }
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
