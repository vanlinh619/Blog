package com.ale.blogcomment.controller;

import com.ale.blog.entity.state.UserRole;
import com.ale.blogcomment.handler.BroadcastMapper;
import com.ale.blogcomment.handler.pojo.BroadcastResponse;
import com.ale.blogcomment.handler.pojo.CommentResponse;
import com.ale.blogcomment.service.CommentService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @MessageMapping("/post/{id}")
    @SendTo("/topic/post/{id}")
    public Object commentToPost(Authentication authentication, @DestinationVariable String id, @Payload BroadcastResponse broadcastResponse) {
        System.out.println(authentication.getPrincipal());
        return broadcastResponse.getPayload();
    }
}
