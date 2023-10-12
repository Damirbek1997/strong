package com.gym.strong.models.crud;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTraineeModel {
    private Long userId;
    private Date birthday;
    private String address;
    private List<Long> trainerIds;
}
