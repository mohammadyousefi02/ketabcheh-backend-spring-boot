package com.mohammadyousefi.ketabcheh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse<String>> handleException(Exception exception) {
        return generateErrorResponse(exception.getMessage(), exception.getHttpStatus());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleBindException(BindException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            httpStatus = responseStatus.value();
        }
        Map<String, String> errorsMap = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return generateErrorResponse(errorsMap, httpStatus);
    }

    private <T> ResponseEntity<ErrorResponse<T>> generateErrorResponse(T message, HttpStatus httpStatus) {
        ErrorResponse<T> errorResponse = new ErrorResponse<>(message, httpStatus.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
