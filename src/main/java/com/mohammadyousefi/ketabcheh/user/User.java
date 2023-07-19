package com.mohammadyousefi.ketabcheh.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.cartItem.CartItem;
import com.mohammadyousefi.ketabcheh.order.Order;
import com.mohammadyousefi.ketabcheh.profile.Profile;
import com.mohammadyousefi.ketabcheh.save.Save;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String email;

    @JsonIgnore
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<CartItem> cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Save> save;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<Order> orders;
}
