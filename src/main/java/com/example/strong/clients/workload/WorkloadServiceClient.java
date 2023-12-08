package com.example.strong.clients.workload;

import com.example.strong.models.WorkloadModel;
import com.example.strong.models.crud.CreateWorkloadModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "workload-service", url = "http://localhost:8083")
public interface WorkloadServiceClient {
    @PostMapping("/workload")
    WorkloadModel create(@RequestBody CreateWorkloadModel createWorkloadModel);

    @PostMapping("/workload/list")
    List<WorkloadModel> create(@RequestBody List<CreateWorkloadModel> createWorkloadModels);
}
