package com.example.strong.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerModel extends UserModel {
    private TrainingTypeModel trainingTypeModel;
}
