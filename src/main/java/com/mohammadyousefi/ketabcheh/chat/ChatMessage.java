package com.mohammadyousefi.ketabcheh.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatMessage {
    @NotNull
    private String username;
    @NotNull
    private String message;
}
