package com.example.strong.clients.workload;

import com.example.strong.exceptions.UnexpectedException;
import com.example.strong.models.WorkloadModel;
import com.example.strong.models.crud.CreateWorkloadModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkloadServiceFallback implements WorkloadServiceClient {
    private static final String ERROR_MSG = "Workload service is unavailable";

    @Override
    public WorkloadModel create(CreateWorkloadModel createWorkloadModel) {
        throw new UnexpectedException(ERROR_MSG);
    }

    @Override
    public List<WorkloadModel> create(List<CreateWorkloadModel> createWorkloadModels) {
        throw new UnexpectedException(ERROR_MSG);
    }
}
