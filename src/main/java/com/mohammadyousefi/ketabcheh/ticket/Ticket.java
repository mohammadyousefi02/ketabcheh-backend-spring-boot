package com.mohammadyousefi.ketabcheh.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.mohammadyousefi.ketabcheh.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    private String title;
    private Boolean isOpen = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties({"id", "username", "role"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIncludeProperties({"id", "username", "role"})
    private User admin;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"ticket"})
    private List<TicketMessage> messages;

}
