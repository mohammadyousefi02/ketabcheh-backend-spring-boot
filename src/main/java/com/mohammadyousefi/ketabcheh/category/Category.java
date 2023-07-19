package com.mohammadyousefi.ketabcheh.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mohammadyousefi.ketabcheh.book.Book;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    @JsonIgnoreProperties({"categories"})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"parentCategory"})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Category> categories;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"categories"})
    private List<Book> books;
}
