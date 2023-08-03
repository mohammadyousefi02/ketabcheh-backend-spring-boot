package com.mohammadyousefi.ketabcheh.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.cartItem.CartItem;
import com.mohammadyousefi.ketabcheh.comment.Comment;
import com.mohammadyousefi.ketabcheh.order.Order;
import com.mohammadyousefi.ketabcheh.profile.Profile;
import com.mohammadyousefi.ketabcheh.rate.Rate;
import com.mohammadyousefi.ketabcheh.save.Save;
import com.mohammadyousefi.ketabcheh.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

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

    @Enumerated(EnumType.STRING)
    private UserRole role;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private List<Rate> rates;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Where(clause = "role = 'USER'")
    @JsonIgnoreProperties({"user"})
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    @Where(clause = "role = 'ADMIN'")
    @JsonIgnoreProperties({"admin"})
    private List<Ticket> adminTickets;
}
