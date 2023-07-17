package com.mohammadyousefi.ketabcheh.exception;

import org.springframework.http.HttpStatus;

public class Exception extends RuntimeException {
    private HttpStatus httpStatus;
    public Exception(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
