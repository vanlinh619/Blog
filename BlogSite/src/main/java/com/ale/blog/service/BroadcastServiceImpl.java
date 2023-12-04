package com.ale.blog.service;

import com.ale.blog.handler.mapper.pojo.request.BroadcastRequest;
import com.ale.blog.handler.mapper.pojo.response.NotificationResponse;
import com.ale.blog.handler.mapper.pojo.response.state.BroadcastType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {
    public static final String BROADCAST_DESTINATION = "/system";

    private final StompSession stompSession;
    private final UserService userService;

    @Value("${account.admin.username}")
    private String admin;

    @Async
    @Override
    public void broadcastComment(String url, BroadcastType type, Object payload) {
        BroadcastRequest broadcastRequest = BroadcastRequest.builder()
                .type(type)
                .payload(payload)
                .build();
        stompSession.send(BROADCAST_DESTINATION + url, broadcastRequest);
    }

    @Override
    public void broadcastNotification(String username, NotificationResponse notificationResponse) {
        BroadcastRequest broadcastRequest = BroadcastRequest.builder()
                .type(BroadcastType.NOTIFICATION)
                .payload(notificationResponse)
                .receiver(username)
                .build();
        stompSession.send(BROADCAST_DESTINATION + "/notification", broadcastRequest);
    }
}
