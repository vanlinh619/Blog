package com.ale.blog.service;

import com.ale.blog.entity.Comment;
import com.ale.blog.handler.mapper.pojo.response.BroadcastResponse;
import com.ale.blog.handler.mapper.pojo.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {
    private final StompSession stompSession;

    @Async
    @Override
    public void broadcast(String topic, BroadcastResponse comment) {
        stompSession.send(topic, comment);
    }
}
