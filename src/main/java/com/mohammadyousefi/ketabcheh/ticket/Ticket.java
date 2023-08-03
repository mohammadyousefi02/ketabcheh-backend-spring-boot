package com.mohammadyousefi.ketabcheh.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"tickets"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnoreProperties({"tickets"})
    private User admin;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"ticket"})
    private List<TicketMessage> messages;

}
