package com.mohammadyousefi.ketabcheh.order;

public interface OrderService {
    Order order(OrderDto orderDto, Boolean useWallet);
    String toggleStatus(Long orderId);
}
