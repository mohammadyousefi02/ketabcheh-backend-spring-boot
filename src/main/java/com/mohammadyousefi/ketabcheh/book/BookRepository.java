package com.mohammadyousefi.ketabcheh.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b from Book b WHERE " +
            "(:title IS NULL OR b.title LIKE %:title%) " +
            "AND (:authorName IS NULL OR b.author.name LIKE %:authorName%) " +
            "AND (:minPrice IS NULL OR b.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR b.price <= :maxPrice)"
    )
    List<Book> findByFilter(String title, String authorName, Integer minPrice, Integer maxPrice);


}
