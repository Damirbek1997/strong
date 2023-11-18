package com.example.strong.models.crud;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateTraineeModel extends CreateUserModel {
    private Date birthday;
    private String address;
}
