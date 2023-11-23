package com.ale.blogcomment.config;

//@Configuration
//@EnableWebSocketSecurity
//public class WebSocketSecurityConfig {
//
//    @Bean
//    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        messages
//                .simpDestMatchers("/comment/**").hasAnyRole(UserRole.ADMIN.name())
//                .simpDestMatchers("/app/**")
//                .hasAnyRole(UserRole.ADMIN.name(), UserRole.CONTENT_CREATOR.name(), UserRole.USER.name());
//
//        return messages.build();
//    }
//}