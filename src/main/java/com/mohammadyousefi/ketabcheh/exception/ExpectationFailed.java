package com.mohammadyousefi.ketabcheh.exception;

import org.springframework.http.HttpStatus;

public class ExpectationFailed extends Exception {
    public ExpectationFailed(String message) {
        super(HttpStatus.EXPECTATION_FAILED, message);
    }
}
