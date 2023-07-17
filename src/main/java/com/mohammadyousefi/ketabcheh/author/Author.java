package com.mohammadyousefi.ketabcheh.author;

import com.mohammadyousefi.ketabcheh.book.Book;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
