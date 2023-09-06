package com.ale.blog.repository;

import com.ale.blog.entity.RefreshToken;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findFirstByToken(String token);
    Optional<RefreshToken> findFirstByUser(User user);
}
