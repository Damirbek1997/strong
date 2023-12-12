package com.example.strong.services.impl;

import com.example.strong.entities.Training;
import com.example.strong.enums.Topics;
import com.example.strong.mappers.WorkloadMapper;
import com.example.strong.models.crud.CreateWorkloadModel;
import com.example.strong.services.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadServiceImpl implements WorkloadService {
    private final JmsTemplate jmsTemplate;
    private final WorkloadMapper workloadMapper;

    @Override
    public void create(Training training) {
        CreateWorkloadModel createWorkloadModel = workloadMapper.toCreateModel(training);
        jmsTemplate.convertAndSend(Topics.CREATE_WORKLOAD_QUEUE_NAME, createWorkloadModel);
        log.debug("Sent a workload with a model {} to Workload service", createWorkloadModel);
    }

    @Override
    public void delete(Collection<Training> trainings) {
        trainings.forEach(training -> {
            CreateWorkloadModel createWorkloadModel = workloadMapper.toCreateModel(training);
            jmsTemplate.convertAndSend(Topics.DELETE_WORKLOAD_QUEUE_NAME, createWorkloadModel);
            log.debug("Sent a workload with a model {} to Workload service", createWorkloadModel);
        });
    }
}
