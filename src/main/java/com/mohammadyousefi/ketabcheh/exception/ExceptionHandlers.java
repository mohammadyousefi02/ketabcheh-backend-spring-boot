package com.mohammadyousefi.ketabcheh.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), exception.getHttpStatus().value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }
}
