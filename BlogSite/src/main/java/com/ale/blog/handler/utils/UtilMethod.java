package com.ale.blog.handler.utils;

import com.ale.blog.entity.User;
import com.ale.blog.security.UserAccess;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class UtilMethod {
    public static User authenticated(Authentication authentication) {
        if(authentication != null && authentication.getPrincipal() instanceof UserAccess userAccess) {
            return userAccess.getUser();
        }
        return null;
    }

    public static Optional<User> owner(Authentication authentication) {
        if(authentication != null && authentication.getPrincipal() instanceof UserAccess userAccess) {
            return Optional.of(userAccess.getUser());
        }
        return Optional.empty();
    }
}
