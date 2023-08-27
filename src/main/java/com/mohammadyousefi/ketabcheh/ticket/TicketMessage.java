package com.mohammadyousefi.ketabcheh.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.mohammadyousefi.ketabcheh.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class TicketMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_message_id")
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "reply_to")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private TicketMessage replyTo;

    @JsonIgnore
    @OneToMany(mappedBy = "replyTo", cascade = CascadeType.ALL)
    private List<TicketMessage> replies;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties({"id", "username", "role"})
    private User user;


}
