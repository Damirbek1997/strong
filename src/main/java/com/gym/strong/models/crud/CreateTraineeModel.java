package com.gym.strong.models.crud;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTraineeModel extends CreateUserModel {
    @NotNull
    private Date birthday;
    @NotNull
    private String address;
    private List<Long> trainerIds;
}
