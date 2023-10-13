package com.ale.blog.service;

import com.ale.blog.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService, EntityService<User, UUID> {
    Optional<UserDetails> loadUserByUuid(UUID uuid);
    User getById(UUID uuid);
    Optional<User> findByUsername(String username);
    Optional<User> findFistUser();
    User create(User user);
    User getByUsername(String username);
}
