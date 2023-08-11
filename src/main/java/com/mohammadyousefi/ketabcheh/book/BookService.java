package com.mohammadyousefi.ketabcheh.book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    List<Book> findByFilter(String title, String authorName, Integer minPrice, Integer maxPrice);

    Optional<Book> findById(Long id);

    Book save(Book book);

    String deleteById(Long id);

    void decreaseStock(Long bookId, int quantity);
}
