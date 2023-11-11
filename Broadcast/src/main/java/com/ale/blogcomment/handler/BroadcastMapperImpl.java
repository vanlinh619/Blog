package com.ale.blogcomment.handler;

import com.ale.blogcomment.handler.pojo.BroadcastResponse;
import com.ale.blogcomment.handler.pojo.CommentResponse;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class BroadcastMapperImpl implements BroadcastMapper {
    private final Gson gson;
    @Override
    public BroadcastResponse toBroadcastResponse(String payload) {
        return gson.fromJson(payload, BroadcastResponse.class);
    }

    @Override
    public Object toObject(BroadcastResponse broadcastResponse) {
        switch (broadcastResponse.getType()) {
            case COMMENT -> {
                return gson.fromJson(broadcastResponse.getPayload().toString(), CommentResponse.class);
            }
            default -> {
                return broadcastResponse.getPayload();
            }
        }
    }
}
