package com.mohammadyousefi.ketabcheh.ticket;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketDto {
    @NotNull
    private String title;
    private Long userId;
}
