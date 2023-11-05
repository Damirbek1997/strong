package com.example.strong.configs;

import com.example.strong.exceptions.BadRequestException;
import com.example.strong.exceptions.UnauthorizedRequestException;
import com.example.strong.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<ResponseModel<Object>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("Incorrect request, msg: {}", ex.getMessage());
        return new ResponseEntity<>(ResponseModel.builder()
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnauthorizedRequestException.class)
    protected ResponseEntity<ResponseModel<Object>> handleNotAuthorizedRequestException(UnauthorizedRequestException ex, WebRequest request) {
        log.error("Incorrect request, msg: {}", ex.getMessage());
        return new ResponseEntity<>(ResponseModel.builder()
                .message(ex.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Throwable.class)
    protected ResponseEntity<ResponseModel<Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("Something went wrong, msg: {}", ex.getMessage());
        return new ResponseEntity<>(ResponseModel.builder()
                .message(ex.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
