package com.ale.blog.repository;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByIdAndAuthor(Long id, User author);
}
