package com.ale.blogcomment.config;

import jakarta.annotation.Nonnull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

public class TopicSubscriptionInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        return Optional.ofNullable(accessor.getCommand())
                .map(stompCommand -> {
                    switch (stompCommand) {
                        case SUBSCRIBE -> {
                            if (!Objects.requireNonNull(accessor.getDestination()).startsWith("/topic/")) {
                                return null;
                            }
                        }
                        case SEND -> {
                            if (!Objects.requireNonNull(accessor.getDestination()).startsWith("/app/")) {
                                return null;
                            }
                        }
                        default -> {
                            return message;
                        }
                    }
                    return message;
                })
                .orElse(null);
    }
}
