package com.mohammadyousefi.ketabcheh.author;

import com.mohammadyousefi.ketabcheh.auth.Admin;
import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorServiceImpl authorService;

    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Response<List<Author>> findAll() {
        return new Response<>(authorService.findAll());
    }

    @GetMapping("/{id}")
    public Response<Author> findById(@PathVariable Long id) {
        Optional<Author> author = authorService.findById(id);
        if (author.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("author"));
        return new Response<>(author.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Authorization
    @Admin
    public Response<Author> createAuthor(@Validated @RequestBody CreateAuthorDto createAuthorDto) {
        return new Response<>(authorService.save(createAuthorDto), HttpStatus.CREATED.value());
    }

    @DeleteMapping("/{id}")
    @Authorization
    @Admin
    public Response<String> deleteById(@PathVariable Long id) {
        return new Response<>(authorService.deleteById(id));
    }
}
