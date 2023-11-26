package com.ale.broadcast.config;

import jakarta.annotation.Nonnull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Objects;
import java.util.Optional;

public class AuthenticationChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        return Optional.ofNullable(accessor.getCommand())
                .map(stompCommand -> {
                    String destination = accessor.getDestination();
//                    switch (stompCommand) {
//                        case SUBSCRIBE -> {
//                            if (destination == null || !destination.startsWith("/topic/")) {
//                                return null;
//                            }
//                        }
//                        case SEND -> {
//                            if (destination == null || !destination.startsWith("/app/")) {
//                                return null;
//                            }
//                        }
//                    }
                    return message;
                })
                .orElse(null);
    }
}
