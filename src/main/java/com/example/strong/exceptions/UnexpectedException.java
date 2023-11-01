package com.example.strong.exceptions;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super(message);
    }
    public UnexpectedException(Throwable ex, String message) {
        super(message, ex);
    }
}
