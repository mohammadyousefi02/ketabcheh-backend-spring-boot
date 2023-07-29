package com.mohammadyousefi.ketabcheh.rate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long id;

    private int rate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"rates"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties({"rates"})
    private Book book;
}
