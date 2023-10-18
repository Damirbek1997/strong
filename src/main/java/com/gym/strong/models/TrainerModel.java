package com.gym.strong.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerModel {
    private Long id;
    private UserModel userModel;
    private TrainingTypeModel trainingTypeModel;
}
