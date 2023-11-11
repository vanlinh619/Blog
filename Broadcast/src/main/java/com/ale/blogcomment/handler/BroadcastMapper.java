package com.ale.blogcomment.handler;

import com.ale.blogcomment.handler.pojo.BroadcastResponse;

public interface BroadcastMapper {
    BroadcastResponse toBroadcastResponse(String payload);
    Object toObject(BroadcastResponse broadcastResponse);
}
