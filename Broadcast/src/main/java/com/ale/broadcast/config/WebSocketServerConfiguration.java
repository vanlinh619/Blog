package com.ale.broadcast.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketServerConfiguration implements WebSocketMessageBrokerConfigurer {

//    @Override
//    protected void configureStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
//        stompEndpointRegistry
//                .addEndpoint("/broadcast")
//                .setAllowedOrigins("https://localhost:8443");
////                .withSockJS();
//    }



    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry
                .addEndpoint("/broadcast")
                .setAllowedOrigins("http://localhost:8080")
                .setHandshakeHandler(new DefaultHandshakeHandler())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/user");
        registry.setApplicationDestinationPrefixes("/system");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthenticationChannelInterceptor());
    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        AuthorizationManager<Message<?>> myAuthorizationRules = AuthenticatedAuthorizationManager
//                .authenticated();
//        AuthorizationChannelInterceptor authz = new AuthorizationChannelInterceptor(myAuthorizationRules);
////        AuthorizationEventPublisher publisher = new SpringAuthorizationEventPublisher(this);
////        authz.setAuthorizationEventPublisher(publisher);
//        registration.interceptors(new SecurityContextChannelInterceptor(), authz);
//    }
}
