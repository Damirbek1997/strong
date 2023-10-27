package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateTraineeModel extends UpdateUserModel {
    @NotNull
    private Long id;
    private Date birthday;
    private String address;
    private List<Long> trainerIds;
}
