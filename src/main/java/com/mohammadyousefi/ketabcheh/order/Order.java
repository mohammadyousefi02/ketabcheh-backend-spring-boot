package com.mohammadyousefi.ketabcheh.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String name;
    private Long zipcode;
    private String province;
    private String city;
    private String address;
    private Boolean delivered = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"order"})
    private List<OrderItem> orderItems;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
