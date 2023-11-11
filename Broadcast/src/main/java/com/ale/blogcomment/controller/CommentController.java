package com.ale.blogcomment.controller;

import com.ale.blogcomment.handler.BroadcastMapper;
import com.ale.blogcomment.handler.pojo.BroadcastResponse;
import com.ale.blogcomment.handler.pojo.CommentResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class CommentController {
    private final BroadcastMapper broadcastMapper;
    private final ModelMapper modelMapper;

    @MessageMapping("/post/{id}")
    @SendTo("/topic/post/{id}")
    public CommentResponse commentToPost(@DestinationVariable String id, @Payload BroadcastResponse broadcastResponse) {
        System.out.println(broadcastResponse);
        System.out.println(id);
//        BroadcastResponse broadcastResponse = broadcastMapper.toBroadcastResponse(payload);
        return modelMapper.map(broadcastResponse.getPayload(), CommentResponse.class);
    }
}
