package com.ale.blog.service;

import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.UserInfoRequest;
import com.ale.blog.security.UserAccessDetails;
import com.ale.blog.security.UserOAuth2AccessDetail;
import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends EntityService<User, UUID> {
    Optional<User> findDefaultAdmin();
    Optional<UserDetails> loadUserByUuid(UUID uuid);
    User getById(UUID uuid);
    Optional<User> findByUsername(String username);
    Optional<User> findFistUser();
    User create(User user);
    User getByUsername(String username);
    Optional<User> findUserByEmail(String email);
    User loadUserByOidcUser(OidcUser oidcUser);
    User updateInfo(@Nonnull User user, @Nonnull UserInfoRequest userInfoRequest);

    UserAccessDetails getUserAccessDetails(User user);

    UserOAuth2AccessDetail getUserOAuth2AccessDetail(User user, OidcUser oidcUser);
}
