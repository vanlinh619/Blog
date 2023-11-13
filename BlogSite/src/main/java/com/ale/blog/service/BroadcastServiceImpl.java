package com.ale.blog.service;

import com.ale.blog.handler.mapper.pojo.response.BroadcastResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {
    private final StompSession stompSession;

    @Async
    @Override
    public void broadcast(String topic, BroadcastResponse comment) {
        stompSession.send(topic, comment);
    }
}
