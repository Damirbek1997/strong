package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateTraineeModel extends CreateUserModel {
    @NotNull
    private Date birthday;
    @NotNull
    private String address;
    private List<Long> trainerIds;
}
