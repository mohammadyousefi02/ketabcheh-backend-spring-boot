package com.mohammadyousefi.ketabcheh.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties({"order"})
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"orders"})
    private User user;
}
