package com.mohammadyousefi.ketabcheh.user;

import com.mohammadyousefi.ketabcheh.profile.Profile;
import jakarta.persistence.*;
import lombok.Data;

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
    private String password;

    @OneToOne(mappedBy = "user")
    private Profile profile;
}
