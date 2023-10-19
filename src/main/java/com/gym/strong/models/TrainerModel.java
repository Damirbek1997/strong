package com.gym.strong.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerModel extends UserModel {
    private Long id;
    private List<TraineeModel> traineeModelList;
}
