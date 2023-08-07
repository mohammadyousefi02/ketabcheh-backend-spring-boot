package com.mohammadyousefi.ketabcheh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse <T> {
    private T message;
    private int status;
    private Long timestamp;
}
