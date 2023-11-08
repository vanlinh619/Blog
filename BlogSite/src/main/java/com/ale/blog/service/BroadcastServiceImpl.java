package com.ale.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {
    private final CompletableFuture<StompSession> futureSession;

    @Override
    public void broadcast(String topic, Message<String> message) {
        futureSession
                .thenAccept(stompSession -> {
                    stompSession.send(topic, message.getPayload());
                    System.out.println("Message sent: " + message.getPayload());
                })
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
    }
}
