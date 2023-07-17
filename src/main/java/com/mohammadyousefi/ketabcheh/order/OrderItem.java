package com.mohammadyousefi.ketabcheh.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.book.Book;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private int quantity;
    private int price;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties({"orderItems"})
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
