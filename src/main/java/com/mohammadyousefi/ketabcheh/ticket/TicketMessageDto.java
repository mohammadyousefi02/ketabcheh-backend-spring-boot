package com.mohammadyousefi.ketabcheh.ticket;

import lombok.Data;

@Data
public class TicketMessageDto {
    private String message;
    private Long userId;
    private Long ticketId;
    private Long replyTo;
}
