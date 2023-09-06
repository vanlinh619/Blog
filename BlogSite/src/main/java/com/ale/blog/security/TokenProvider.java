package com.ale.blog.security;

import java.util.Optional;

public interface TokenProvider {

    String generateToken(UserAccess userAccess);

    Optional<String> getUserId(String token);
}
