package com.ale.blogcomment.controller;

import com.ale.blogcomment.handler.BroadcastMapper;
import com.ale.blogcomment.handler.pojo.BroadcastResponse;
import com.ale.blogcomment.handler.pojo.CommentResponse;
import com.ale.blogcomment.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @MessageMapping("/post/{id}")
    @SendTo("/topic/post/{id}")
    public Object commentToPost(@DestinationVariable String id, @Payload BroadcastResponse broadcastResponse, SimpMessageHeaderAccessor headerAccessor) {
        if (!commentService.isAdminBroadcast(broadcastResponse)) {
            throw new RuntimeException("User role is not admin");
        }
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println(sessionId);
        return broadcastResponse.getPayload();
    }
}
