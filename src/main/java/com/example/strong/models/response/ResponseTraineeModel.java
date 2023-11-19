package com.example.strong.models.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseTraineeModel {
    private String username;
    private String firstName;
    private String lastName;
}
