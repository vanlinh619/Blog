package com.ale.blog.service;

import com.ale.blog.entity.Comment;
import com.ale.blog.handler.mapper.pojo.response.BroadcastResponse;
import com.ale.blog.handler.mapper.pojo.response.CommentResponse;
import org.springframework.messaging.Message;

public interface BroadcastService {
    default void broadcast(String topic, BroadcastResponse broadcastResponse) {}
}
