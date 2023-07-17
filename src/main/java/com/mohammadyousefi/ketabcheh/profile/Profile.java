package com.mohammadyousefi.ketabcheh.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"profile"})
    private User user;

    @ManyToMany
    @JoinTable(name = "book_profile",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Book> books;
}
