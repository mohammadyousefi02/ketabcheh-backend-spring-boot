package com.mohammadyousefi.ketabcheh.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
