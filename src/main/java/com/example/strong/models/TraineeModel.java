package com.example.strong.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class TraineeModel extends UserModel {
    private Date birthday;
    private String address;
    private List<ResponseTrainerModel> trainerModels;
}
