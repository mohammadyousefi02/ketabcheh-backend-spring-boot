package com.mohammadyousefi.ketabcheh.auth;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
