package com.example.strong.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    CLIENT_SIDE_MSG("Incorrect request, msg: {}"),
    SERVER_SIDE_MSG("Something went wrong, msg: {}"),
    ;

    private final String body;
}
