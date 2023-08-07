package com.mohammadyousefi.ketabcheh.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupDto {
    @NotNull
    @Min(value = 3)
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Min(value = 8)
    private String password;
}
