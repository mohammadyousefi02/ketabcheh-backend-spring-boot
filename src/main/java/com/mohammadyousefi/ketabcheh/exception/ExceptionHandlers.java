package com.mohammadyousefi.ketabcheh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return generateErrorResponse(exception.getMessage(), exception.getHttpStatus());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            httpStatus = responseStatus.value();
        }
        return generateErrorResponse(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), httpStatus);
    }

    private ResponseEntity<ErrorResponse> generateErrorResponse(String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(message, httpStatus.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
