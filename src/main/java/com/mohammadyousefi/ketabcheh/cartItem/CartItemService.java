package com.mohammadyousefi.ketabcheh.cartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    Optional<List<CartItem>> findByUserId(Long userId);
    String addToCart(Long userId, Long bookId);
    String addPdfBookToCart(Long userId, Long bookId);
    String decreaseQuantity(Long userId, Long bookId);
    String removeItem(Long userId, Long bookId, CartItemType cartItemType);
    String clear(Long userId);
}
