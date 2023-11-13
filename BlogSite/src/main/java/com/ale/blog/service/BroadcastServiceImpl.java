package com.ale.blog.service;

import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.response.BroadcastResponse;
import com.ale.blog.handler.mapper.pojo.response.state.BroadcastType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {
    private final StompSession stompSession;
    private final UserService userService;

    @Value("${account.admin.username}")
    private String admin;

    @Async
    @Override
    public void broadcast(String topic, BroadcastType type, Object payload) {
        User user = userService.getByUsername(admin);
        User content = userService.getByUsername("content");
        BroadcastResponse broadcastResponse = BroadcastResponse.builder()
                .userId(user.getUuid().toString())
                .type(type)
                .payload(payload)
                .build();
        stompSession.send(topic, broadcastResponse);
        broadcastResponse.setUserId(content.getUuid().toString());
        stompSession.send(topic.replace("app", "topic"), broadcastResponse);
    }
}
