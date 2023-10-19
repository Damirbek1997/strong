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
public class UpdateTraineeModel extends UpdateUserModel {
    @NotNull
    private Long id;
    private Date birthday;
    private String address;
    private List<Long> trainerIds;
}
