package com.ale.blog.handle;

import com.ale.blog.entity.User;
import com.ale.blog.entity.state.OAuthProvider;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.repository.RefreshTokenRepository;
import com.ale.blog.repository.UserRepository;
import com.ale.blog.service.RefreshTokenService;
import com.ale.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.time.Instant;

@TestComponent
public class TestUtil {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public static final String USERNAME = "Test";
    public static final String PASSWORD = "Test";
    public static final String EMAIL = "Test@email.com";

    public User createAdminUser() {
        User test = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .lastName(USERNAME)
                .firstName(USERNAME)
                .email(EMAIL)
                .phoneNumber("0999999999")
                .intro("test")
                .registered(Instant.now())
                .role(UserRole.ADMIN)
                .provider(OAuthProvider.LOCAL)
                .build();
        userService.create(test);
        return test;
    }

    public void cleanUser() {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    public static String toJson(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static  <T> T readObject(String value, Class<T> clazz) throws JsonProcessingException {
        return new ObjectMapper().readValue(value, clazz);
    }
}
