package com.gym.strong.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeModel {
    private Long id;
    private UserModel userModel;
    private Date birthday;
    private String address;
    private List<TrainerModel> trainerModels;
}
