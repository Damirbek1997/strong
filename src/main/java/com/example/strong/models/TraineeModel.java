package com.example.strong.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeModel extends UserModel {
    private Date birthday;
    private String address;
}
