package com.example.strong.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean active;
}
