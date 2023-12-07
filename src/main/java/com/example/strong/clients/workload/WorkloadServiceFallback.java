package com.example.strong.clients.workload;

import com.example.strong.models.WorkloadModel;
import com.example.strong.models.crud.CreateWorkloadModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class WorkloadServiceFallback implements WorkloadServiceClient {
    @Override
    public WorkloadModel create(CreateWorkloadModel createWorkloadModel) {
        return new WorkloadModel();
    }

    @Override
    public List<WorkloadModel> create(List<CreateWorkloadModel> createWorkloadModels) {
        return Collections.emptyList();
    }
}
