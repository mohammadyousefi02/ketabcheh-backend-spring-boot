package com.mohammadyousefi.ketabcheh.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @OneToMany(mappedBy = "replyTo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TicketMessage> replies;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonIgnoreProperties({"messages"})
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
