package com.ale.broadcast.handler;

import com.ale.blog.handler.mapper.pojo.request.BroadcastRequest;

public interface BroadcastMapper {
    BroadcastRequest toBroadcastResponse(String payload);
    Object toObject(BroadcastRequest broadcastRequest);
}
