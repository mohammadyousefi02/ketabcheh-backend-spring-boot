package com.mohammadyousefi.ketabcheh.order;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Authorization
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Order> order(HttpServletRequest request, @RequestBody OrderDto orderDto) {
        orderDto.setUserId((Long) request.getAttribute("userId"));
        return new Response<>(orderService.order(orderDto), HttpStatus.CREATED.value());
    }
}
