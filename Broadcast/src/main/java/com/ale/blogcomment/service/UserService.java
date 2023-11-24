package com.ale.blogcomment.service;

import com.ale.blog.entity.User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends com.ale.blog.service.UserService {
    Optional<User> findByUuid(UUID uuid);
}
