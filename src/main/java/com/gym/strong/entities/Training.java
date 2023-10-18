package com.gym.strong.entities;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    private Long id;
    private Trainee trainee;
    private Trainer trainer;
    private String trainingName;
    private TrainingType trainingType;
    private Date trainingDate;
    private Long trainingDuration;
}
