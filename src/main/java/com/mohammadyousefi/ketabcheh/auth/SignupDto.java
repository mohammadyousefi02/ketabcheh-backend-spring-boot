package com.mohammadyousefi.ketabcheh.auth;

import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String email;
    private String password;
}
