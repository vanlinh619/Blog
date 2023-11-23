package com.ale.blogcomment.reponsitory;

import com.ale.blog.entity.User;
import com.ale.blog.entity.state.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByOrderByUuid();
    Optional<User> findFirstByRole(UserRole role);
    Optional<User> findUserByUsername(String username);
    Optional<User> findByUuid(UUID uuid);
    Optional<User> findUserByEmail(String email);
}
