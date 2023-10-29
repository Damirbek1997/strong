package com.example.strong.exceptions;

public class NotAuthorizedRequestException extends RuntimeException {
    public NotAuthorizedRequestException(String message) {
        super(message);
    }
}
