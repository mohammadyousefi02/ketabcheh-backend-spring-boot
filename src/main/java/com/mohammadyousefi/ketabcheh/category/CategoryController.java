package com.mohammadyousefi.ketabcheh.category;

import com.mohammadyousefi.ketabcheh.auth.Admin;
import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Response<List<Category>> findAll() {
        return new Response<>(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public Response<Category> findById(@PathVariable Long id) {
        return new Response<>(categoryService.findById(id));
    }

    @PostMapping
    @Authorization
    @Admin
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Category> create(@Validated @RequestBody CreateCategoryDto createCategoryDto) {
        return new Response<>(categoryService.save(createCategoryDto), HttpStatus.CREATED.value());
    }

    @DeleteMapping("/{id}")
    @Authorization
    @Admin
    public Response<String> deleteById(@PathVariable Long id) {
        return new Response<>(categoryService.deleteById(id));
    }
}
