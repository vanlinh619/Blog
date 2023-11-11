package com.ale.blog.config;

import com.ale.blog.service.BroadcastService;
import com.ale.blog.service.BroadcastServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Configuration
public class BroadcastConfig {
    @Value("${microservice.broadcast}")
    private Boolean broadcast;
    @Value("${microservice.url}")
    private String broadcastServiceUrl;

    @Bean
    public BroadcastService broadcastService() {
        if(broadcast) {
            WebSocketClient client = new StandardWebSocketClient();
            WebSocketStompClient stompClient = new WebSocketStompClient(client);
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());
            StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {};
            CompletableFuture<StompSession> future = stompClient.connectAsync(broadcastServiceUrl, sessionHandler);
            return new BroadcastServiceImpl(future.join());
        }
        return new BroadcastService() {};
    }
}
