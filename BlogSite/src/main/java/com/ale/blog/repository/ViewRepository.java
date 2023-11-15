package com.ale.blog.repository;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
    Optional<View> findFirstByPostAndUserOrderByTimestampDesc(Post post, User user);
    Optional<View> findFirstByPostAndIpaddressOrderByTimestampDesc(Post post, String ipaddress);
}
