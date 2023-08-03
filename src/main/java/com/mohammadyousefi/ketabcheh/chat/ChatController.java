package com.mohammadyousefi.ketabcheh.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @MessageMapping("/public")
    @SendTo("/topic/public")
    public ChatMessage chatMessage(ChatMessage chatMessage) {
        return chatMessage;
    }
}
