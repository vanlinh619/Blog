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
            return userAccess.getUser();
        }
        return null;
    }

    public static Optional<User> owner(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserAccess userAccess) {
            return Optional.of(userAccess.getUser());
        }
        return Optional.empty();
    }

    public static Optional<User> owner(Authentication authentication, ImageService imageService) {
        if (authentication != null && authentication.getPrincipal() instanceof UserAccess userAccess) {
            userAccess.getUser().setAvatar(imageService.getAvatar(userAccess.getUser()).orElse(null));
            return Optional.of(userAccess.getUser());
        }
        return Optional.empty();
    }

    public static boolean isOwner(@Nullable User owner, @Nonnull User user) {
        return owner != null && owner.getUuid().equals(user.getUuid());
    }
}
