package com.mohammadyousefi.ketabcheh.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private T data;
    private int status = HttpStatus.OK.value();

    public Response(T data) {
        this.data = data;
    }
}
