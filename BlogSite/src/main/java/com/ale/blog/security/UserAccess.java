package com.ale.blog.security;

import com.ale.blog.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserAccess extends UserDetails {
    UUID getUuid();
    User getUser();
}
