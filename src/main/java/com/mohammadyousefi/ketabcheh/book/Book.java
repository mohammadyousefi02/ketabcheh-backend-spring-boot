package com.mohammadyousefi.ketabcheh.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.author.Author;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;
    private Long publishDate;
    private String size;
    private String type;
    private int stock;
    private String description;
    private int price;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"books"})
    private Author author;
}
