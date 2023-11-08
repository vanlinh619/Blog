package com.ale.blog.service;

import org.springframework.messaging.Message;

public interface BroadcastService {
    default void broadcast(String topic, Message<String> message) {}
}
