package com.ale.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final BroadcastService broadcastService;

    @Override
    public void comment(String message) {
        System.out.println("Received Message: " + message);
        Message<String> ms = new GenericMessage<>(message);
        broadcastService.broadcast("/topic/post/41-bieu-thuc-lambda-trong-java-8-lambda-expressions", ms);
    }
}
