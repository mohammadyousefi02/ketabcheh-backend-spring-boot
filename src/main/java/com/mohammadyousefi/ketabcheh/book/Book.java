package com.mohammadyousefi.ketabcheh.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.mohammadyousefi.ketabcheh.author.Author;
import com.mohammadyousefi.ketabcheh.category.Category;
import com.mohammadyousefi.ketabcheh.image.Image;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    private String filename;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"books"})
    private Author author;

    @OneToMany(mappedBy = "book")
    @JsonIncludeProperties({"filename"})
    private List<Image> images;

    @ManyToMany
    @JoinTable(name = "book_category",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    @JsonIgnoreProperties({"products"})
    private List<Category> categories;

}
