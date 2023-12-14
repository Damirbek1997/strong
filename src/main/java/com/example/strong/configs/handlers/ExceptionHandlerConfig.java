package com.example.strong.configs.handlers;

import com.example.strong.exceptions.BadRequestException;
import com.example.strong.models.ErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    protected ErrorModel handleBadRequestException(BadRequestException ex) {
        log.error("Bad request, msg: {}", ex.getMessage());
        return ErrorModel.builder()
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadCredentialsException.class)
    protected ErrorModel handleRuntimeException(BadCredentialsException ex) {
        log.error("Bad credentials, msg: {}", ex.getMessage());
        return ErrorModel.builder()
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ErrorModel handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Invalid request, msg: {}", ex.getMessage());
        return ErrorModel.builder()
                .message(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    protected ErrorModel handleException(Exception ex) {
        log.error("Something went wrong, msg: {}", ex.getMessage());
        return ErrorModel.builder()
                .message(ex.getMessage())
                .build();
    }
}
