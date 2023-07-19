package com.mohammadyousefi.ketabcheh.user;

import com.mohammadyousefi.ketabcheh.auth.LoginDto;
import com.mohammadyousefi.ketabcheh.auth.SignupDto;

import java.util.Optional;

public interface UserService {
    String signUp(SignupDto signupDto);
    String login(LoginDto loginDto);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    String deleteById(Long id);
}
