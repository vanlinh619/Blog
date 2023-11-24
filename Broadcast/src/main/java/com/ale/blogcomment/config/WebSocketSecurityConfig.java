package com.ale.blogcomment.config;

import com.ale.blog.entity.state.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

//@Configuration
//@EnableWebSocketSecurity
//public class WebSocketSecurityConfig {
//
//    @Bean
//    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        return messages
//                .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT, SimpMessageType.UNSUBSCRIBE).permitAll()
//                .simpDestMatchers("/system/**").hasAnyRole(UserRole.ADMIN.name())
//                .anyMessage().permitAll()
//                .build();
////                .simpDestMatchers("/app/**")
////                .hasAnyRole(UserRole.ADMIN.name(), UserRole.CONTENT_CREATOR.name(), UserRole.USER.name());
//    }
//}