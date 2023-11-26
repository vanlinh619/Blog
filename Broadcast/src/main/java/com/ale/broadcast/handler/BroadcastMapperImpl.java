package com.ale.broadcast.handler;

import com.ale.blog.handler.mapper.pojo.request.BroadcastRequest;
import com.ale.blog.handler.mapper.pojo.response.CommentResponse;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BroadcastMapperImpl implements BroadcastMapper {
    private final Gson gson;
    @Override
    public BroadcastRequest toBroadcastResponse(String payload) {
        return gson.fromJson(payload, BroadcastRequest.class);
    }

    @Override
    public Object toObject(BroadcastRequest broadcastRequest) {
        switch (broadcastRequest.getType()) {
            case COMMENT -> {
                return gson.fromJson(broadcastRequest.getPayload().toString(), CommentResponse.class);
            }
            default -> {
                return broadcastRequest.getPayload();
            }
        }
    }
}
