package com.example.strong.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrainingModel {
    private Long id;
    private String trainingName;
    private TrainingTypeModel trainingTypeModel;
    private Date trainingDate;
    private Long trainingDuration;
    private String trainerName;
}
