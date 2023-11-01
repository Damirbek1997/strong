package com.example.strong.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseCredentialsModel {
    private String username;
    private String password;
}
