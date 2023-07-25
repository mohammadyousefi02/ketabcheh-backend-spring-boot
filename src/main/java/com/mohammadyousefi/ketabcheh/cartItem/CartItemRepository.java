package com.mohammadyousefi.ketabcheh.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUser_IdAndBook_IdAndType(Long userId, Long bookId, CartItemType type);
    void deleteByUser_Id(Long userId);
    Optional<List<CartItem>> findByUser_Id(Long userId);
}
