package com.ale.blogcomment.service;

import com.ale.blogcomment.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> findByUuid(UUID uuid);
}
