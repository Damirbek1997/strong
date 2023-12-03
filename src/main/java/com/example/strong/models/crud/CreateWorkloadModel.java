package com.example.strong.models.crud;

import com.example.strong.enums.WorkloadActionType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkloadModel {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Boolean isActive;
    private Date trainingDate;
    private Long trainingDuration;
    private WorkloadActionType actionType;
}
