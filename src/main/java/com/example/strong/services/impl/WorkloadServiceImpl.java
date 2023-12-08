package com.example.strong.services.impl;

import com.example.strong.entities.Training;
import com.example.strong.enums.WorkloadActionType;
import com.example.strong.exceptions.UnexpectedException;
import com.example.strong.models.crud.CreateWorkloadModel;
import com.example.strong.services.WorkloadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadServiceImpl implements WorkloadService {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void create(Training training, WorkloadActionType workloadActionType) {
        CreateWorkloadModel createWorkloadModel = toCreateWorkloadModel(training, workloadActionType);
        sendMessage("workload-create", createWorkloadModel);
        log.info("Sent a workload with a model {} to Workload service", createWorkloadModel);
    }

    @Override
    public void create(List<Training> trainings, WorkloadActionType workloadActionType) {
        List<CreateWorkloadModel> createWorkloadModels = new ArrayList<>();

        for (Training training : trainings) {
            createWorkloadModels.add(toCreateWorkloadModel(training, workloadActionType));
        }

        sendMessage("workload-create-list", createWorkloadModels);
        log.info("Sent workload list with a model {} to Workload service", createWorkloadModels);
    }

    private void sendMessage(String destination, Object value) {
        try {
            jmsTemplate.convertAndSend(destination, objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            throw new UnexpectedException(e.getMessage());
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
