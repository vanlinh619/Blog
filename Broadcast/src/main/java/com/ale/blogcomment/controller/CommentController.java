package com.ale.blogcomment.controller;

import com.ale.blogcomment.handler.BroadcastMapper;
import com.ale.blogcomment.handler.pojo.BroadcastResponse;
import com.ale.blogcomment.handler.pojo.CommentResponse;
import com.ale.blogcomment.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @SubscribeMapping("/topic")
    public void rejectRequest() {
        throw new RuntimeException("Bad request");
    }

    @MessageMapping("/post/{id}")
    @SendTo("/topic/post/{id}")
    public Object commentToPost(@DestinationVariable String id, @Payload BroadcastResponse broadcastResponse) {
        if (!commentService.isAdminBroadcast(broadcastResponse)) {
            throw new RuntimeException("User role is not admin");
        }
        return broadcastResponse.getPayload();
    }
}
