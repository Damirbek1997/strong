package com.gym.strong.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeModel extends UserModel {
    private Long id;
    private Date birthday;
    private String address;
    private List<TrainerModel> trainerModels;
}
