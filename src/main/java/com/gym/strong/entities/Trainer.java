package com.gym.strong.entities;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    private Long id;
    private User user;
    private TrainingType trainingType;
    private Set<Trainee> trainees;
}
