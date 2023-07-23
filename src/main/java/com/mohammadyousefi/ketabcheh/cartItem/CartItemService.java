package com.mohammadyousefi.ketabcheh.cartItem;

public interface CartItemService {
    String addToCart(Long userId, Long bookId);
    String addPdfBookToCart(Long userId, Long bookId);
    String decreaseQuantity(Long userId, Long bookId);
    String removeItem(Long userId, Long bookId, CartItemType cartItemType);
    String clear(Long userId);
}
