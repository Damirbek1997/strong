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
public class CreateTraineeModel {
    @NotNull
    private CreateUserModel createUserModel;
    @NotNull
    private Date birthday;
    @NotNull
    private String address;
    private List<Long> trainerIds;
}
