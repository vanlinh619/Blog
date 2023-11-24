package com.ale.blog.security;

import com.ale.blog.entity.User;

import java.util.Optional;

public interface TokenProvider {

    String generateToken(User user, Long expiration);

    String generateToken(User user);

    String generateToken(UserAccess userAccess);

    Optional<String> getUserId(String token);
}
