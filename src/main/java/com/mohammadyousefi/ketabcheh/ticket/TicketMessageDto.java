package com.mohammadyousefi.ketabcheh.ticket;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketMessageDto {
    @NotNull
    private String message;
    private Long userId;
    @NotNull
    private Long ticketId;
    private Long replyTo;
}
