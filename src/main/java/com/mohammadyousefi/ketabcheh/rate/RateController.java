package com.mohammadyousefi.ketabcheh.rate;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rates")
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/{bookId}")
    @Authorization
    public Response<String> save(HttpServletRequest request, @PathVariable Long bookId, @RequestBody RateDto rateDto) {
        rateDto.setUserId((Long) request.getAttribute("userId"));
        rateDto.setBookId(bookId);
        return new Response<>(rateService.save(rateDto), HttpStatus.CREATED.value());
    }
}
