package com.mohammadyousefi.ketabcheh.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.book.Book;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String filename;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties({"images"})
    private Book book;
}
