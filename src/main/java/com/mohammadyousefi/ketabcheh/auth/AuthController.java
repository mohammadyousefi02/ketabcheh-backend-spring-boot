package com.mohammadyousefi.ketabcheh.auth;

import com.mohammadyousefi.ketabcheh.response.Response;
import com.mohammadyousefi.ketabcheh.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<String> signup(@Validated @RequestBody SignupDto signupDto) {
        String token = userService.signUp(signupDto);
        return new Response<>(token, HttpStatus.CREATED.value());
    }

    @PostMapping("/login")
    public Response<String> login(@Validated @RequestBody LoginDto loginDto) {
        String token = userService.login(loginDto, false);
        return new Response<>(token);
    }

    @PostMapping("/admin")
    public Response<String> adminLogin(@Validated @RequestBody LoginDto loginDto) {
        String token = userService.login(loginDto, true);
        return new Response<>(token);
    }


}
