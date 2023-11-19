package com.example.strong.models.response;

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
