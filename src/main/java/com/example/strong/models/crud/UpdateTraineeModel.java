package com.example.strong.models.crud;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTraineeModel {
    @NotNull
    private Long id;
    private UpdateUserModel updateUserModel;
    private Date birthday;
    private String address;
    private List<Long> trainerIds;
}
