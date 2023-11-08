package com.ale.blogcomment.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CommentController {

    @MessageMapping("/post/{id}")
    @SendTo("/topic/post/{id}")
    public String commentToPost(@DestinationVariable String id, @Payload String message) {
        System.out.println(message);
        System.out.println(id);
        return id+"/"+message;
    }
}
