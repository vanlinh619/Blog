package com.ale.broadcast.controller;

import com.ale.blog.handler.mapper.pojo.request.BroadcastRequest;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class BroadcastController {
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/post/{id}")
    @SendTo("/topic/post/{id}")
    public Object commentToPost(@DestinationVariable String id, @Payload BroadcastRequest broadcastRequest) {
        return broadcastRequest.getPayload();
    }

    @MessageMapping("/notification")
    public void notification(@Payload BroadcastRequest broadcastRequest) {
        simpMessagingTemplate.convertAndSendToUser(
                broadcastRequest.getReceiver(),
                "queue/notification",
                broadcastRequest.getPayload()
        );
    }
}
