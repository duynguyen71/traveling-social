package com.tv.socket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;


    // broadcast for public chat room
    @MessageMapping("/news")
    @SendTo("/topic/news")
    public String broadcastNews(@Payload String message) {
        return message;
    }

    @MessageMapping("/hello")
    public void send(SimpMessageHeaderAccessor sha, @Payload String username) {
        String message = "Hello from " + sha.getUser().getName();
        simpMessagingTemplate.convertAndSendToUser("kddevit", "/queue/messages", message);
    }

}
