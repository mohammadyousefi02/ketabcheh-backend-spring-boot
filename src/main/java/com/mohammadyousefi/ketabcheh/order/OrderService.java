package com.mohammadyousefi.ketabcheh.order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findByUserId(Long userId);
    Order order(OrderDto orderDto, Boolean useWallet);
    String toggleStatus(Long orderId);
}
