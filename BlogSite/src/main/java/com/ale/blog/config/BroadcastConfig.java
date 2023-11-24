package com.ale.blog.config;

import com.ale.blog.entity.User;
import com.ale.blog.security.JwtTokenProvider;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.BroadcastService;
import com.ale.blog.service.BroadcastServiceImpl;
import com.ale.blog.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Configuration
public class BroadcastConfig {
    @Value("${microservice.broadcast}")
    private Boolean broadcast;
    @Value("${microservice.url}")
    private String broadcastServiceUrl;
    @Value("${account.admin.username}")
    private String admin;

    @Bean
    public BroadcastService broadcastService(UserService userService, AuthenticationProvider authenticationProvider, JwtTokenProvider jwtTokenProvider) {
        return Optional.ofNullable(broadcast ? true : null)
                .flatMap(b -> userService.findByUsername(admin))
                .<BroadcastService>map(user -> {
                    String token = jwtTokenProvider.generateToken(user);

                    WebSocketClient client = new SockJsClient(List.of(
                            new WebSocketTransport(new StandardWebSocketClient())
                    ));
                    WebSocketStompClient stompClient = new WebSocketStompClient(client);
                    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
                    StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {};
                    WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
                    headers.add(WebSocketHttpHeaders.AUTHORIZATION, "Bearer " + token);
                    CompletableFuture<StompSession> future = stompClient
                            .connectAsync(broadcastServiceUrl, headers, sessionHandler);
                    return new BroadcastServiceImpl(future.join(), userService);
                })
                .orElseGet(() -> new BroadcastService() {});
    }
}
