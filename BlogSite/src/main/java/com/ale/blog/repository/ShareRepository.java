package com.ale.blog.repository;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.Share;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, Long> {
    Optional<Share> findShareByDocumentAndShareWith(Document document, User shareWith);
    Optional<Share> findShareByPostAndShareWith(Post post, User shareWith);
}
