package com.mohammadyousefi.ketabcheh.save;

import com.mohammadyousefi.ketabcheh.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/save")
public class SaveController {
    private final SaveService saveService;

    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<String> save(@RequestBody SaveDto saveDto) {
        return new Response<>(saveService.save(saveDto.getUserId(), saveDto.getBookId()), HttpStatus.CREATED.value());
    }

    @DeleteMapping
    public Response<String> unsave(@RequestBody SaveDto saveDto) {
        return new Response<>(saveService.unsave(saveDto.getUserId(), saveDto.getBookId()));
    }
}
