package com.ale.blog.repository;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findFirstBySlug(String slug);
    Page<Post> findAllByAuthor(User author, Pageable pageable);
}
