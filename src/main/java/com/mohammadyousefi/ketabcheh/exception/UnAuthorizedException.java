package com.mohammadyousefi.ketabcheh.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends Exception {
    public UnAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
