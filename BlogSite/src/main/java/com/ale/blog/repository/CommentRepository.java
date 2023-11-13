package com.ale.blog.repository;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostAndSuperParentIsNull(Post post, Pageable pageable);
    Page<Comment> findAllByPostAndSuperParent(Post post, Comment supperParent, Pageable pageable);
    Optional<Comment> findCommentByIdAndPost(Long id, Post post);
}
