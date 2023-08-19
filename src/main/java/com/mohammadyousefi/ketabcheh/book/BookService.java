package com.mohammadyousefi.ketabcheh.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Page<Book> findByFilter(String title, String authorName, Integer minPrice, Integer maxPrice, Pageable pageable);

    Optional<Book> findById(Long id);

    Book save(Book book);

    String deleteById(Long id);

    void decreaseStock(Long bookId, int quantity);
}
