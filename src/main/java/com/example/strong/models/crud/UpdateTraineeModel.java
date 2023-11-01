package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateTraineeModel extends UpdateUserModel {
    private Date birthday;
    private String address;
}
