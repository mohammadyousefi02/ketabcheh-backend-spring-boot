package com.mohammadyousefi.ketabcheh.save;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/save")
public class SaveController {
    private final SaveService saveService;

    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @GetMapping
    @Authorization
    public Response<List<Save>> findByUserId(HttpServletRequest request) {
        return new Response<>(saveService.findByUserId((Long) request.getAttribute("userId")));
    }

    @PostMapping("/{bookId}")
    @Authorization
    @ResponseStatus(HttpStatus.CREATED)
    public Response<String> save(HttpServletRequest request, @PathVariable Long bookId) {
        return new Response<>(saveService.save((Long) request.getAttribute("userId"), bookId), HttpStatus.CREATED.value());
    }

    @DeleteMapping("/{bookId}")
    @Authorization
    public Response<String> unsave(HttpServletRequest request, @PathVariable Long bookId) {
        return new Response<>(saveService.unsave((Long) request.getAttribute("userId"), bookId));
    }
}
