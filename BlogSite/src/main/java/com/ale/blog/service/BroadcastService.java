package com.ale.blog.service;

import com.ale.blog.handler.mapper.pojo.response.NotificationResponse;
import com.ale.blog.handler.mapper.pojo.response.state.BroadcastType;

public interface BroadcastService {
    default void broadcastComment(String url, BroadcastType type, Object payload) {}
    default void broadcastNotification(String username, NotificationResponse notificationResponse){}
}
