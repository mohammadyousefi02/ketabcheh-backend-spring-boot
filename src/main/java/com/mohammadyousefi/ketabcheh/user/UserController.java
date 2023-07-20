package com.mohammadyousefi.ketabcheh.user;

import com.mohammadyousefi.ketabcheh.auth.Admin;
import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) throw new NotFoundException("there is no user with this id");
        return userOptional.get();
    }

    @DeleteMapping("/{id}")
    @Authorization
    @Admin
    public Response<String> deleteById(@PathVariable Long id) {
        String res = userService.deleteById(id);
        return new Response<>(res, HttpStatus.OK.value());
    }
}
