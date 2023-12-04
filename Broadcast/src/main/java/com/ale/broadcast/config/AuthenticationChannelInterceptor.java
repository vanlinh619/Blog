package com.ale.broadcast.config;

import com.ale.blog.entity.User;
import com.ale.blog.security.UserAccess;
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
        System.out.println(accessor.getCommand() + ": " + accessor.getDestination());
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
//                            if (destination == null || !destination.startsWith("/system/")) {
//                                return null;
//                            }
//                        }
//                    }
                    return message;
                })
                .orElse(null);
    }
}
