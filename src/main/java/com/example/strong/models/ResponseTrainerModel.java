package com.example.strong.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTrainerModel {
    private String username;
    private String firstName;
    private String lastName;
    private Long specializationId;
}
