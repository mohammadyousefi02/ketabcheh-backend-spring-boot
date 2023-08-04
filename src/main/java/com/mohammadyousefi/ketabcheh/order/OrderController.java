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
        return orderHandler(request, orderDto, false);
    }

    @PostMapping("wallet")
    @Authorization
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Order> orderByWallet(HttpServletRequest request, @RequestBody OrderDto orderDto) {
        return orderHandler(request, orderDto, true);
    }

    private Response<Order> orderHandler(HttpServletRequest request, OrderDto orderDto, Boolean useWallet) {
        orderDto.setUserId((Long) request.getAttribute("userId"));
        return new Response<>(orderService.order(orderDto, useWallet), HttpStatus.CREATED.value());
    }
}
