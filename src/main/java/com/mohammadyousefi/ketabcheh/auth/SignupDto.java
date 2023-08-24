package com.mohammadyousefi.ketabcheh.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {
    @NotNull
    @Size(min = 3, message = "username should have at least 3 characters")
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;
}
