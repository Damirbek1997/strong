package com.example.strong.models.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCredentialsModel {
    private String username;
    private String password;
}