package com.ale.blog.handler.utils;

import com.ale.blog.entity.User;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.ImageService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class UserUtil {
    public static User authenticated(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserAccess userAccess) {
            return userAccess.getCurrentUser();
        }
        return null;
    }

    public static Optional<User> owner(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserAccess userAccess) {
            return Optional.of(userAccess.getCurrentUser());
        }
        return Optional.empty();
    }

    public static boolean isOwner(@Nullable User owner, @Nonnull User user) {
        return owner != null && owner.getUuid().equals(user.getUuid());
    }
}
