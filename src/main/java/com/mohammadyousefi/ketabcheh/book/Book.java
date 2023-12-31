package com.mohammadyousefi.ketabcheh.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.mohammadyousefi.ketabcheh.author.Author;
import com.mohammadyousefi.ketabcheh.cartItem.CartItem;
import com.mohammadyousefi.ketabcheh.category.Category;
import com.mohammadyousefi.ketabcheh.comment.Comment;
import com.mohammadyousefi.ketabcheh.image.Image;
import com.mohammadyousefi.ketabcheh.profile.Profile;
import com.mohammadyousefi.ketabcheh.rate.Rate;
import com.mohammadyousefi.ketabcheh.save.Save;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"books"})
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIncludeProperties({"filename"})
    private List<Image> images;

    @ManyToMany
    @JoinTable(name = "book_category", joinColumns = {@JoinColumn(name = "book_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @JsonIgnoreProperties({"books", "parentCategory", "categories"})
    private List<Category> categories;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"book"})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"book"})
    private List<Rate> rates;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Save> saves;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItem> cartItems;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Profile> profiles;

    public String getRates() {
        AtomicInteger rates = new AtomicInteger(0);
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        float finalRates = 0;
        if (this.rates != null) {
            this.rates.forEach(rate -> rates.addAndGet(rate.getRate()));
            finalRates = (float) rates.get() / (this.rates.isEmpty() ? 1 : this.rates.size());
        }
        return decimalFormat.format(finalRates);
    }

}
