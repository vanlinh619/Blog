package com.ale.blog.repository;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByIdAndAuthor(Long id, User author);
    @Transactional
    @Modifying
    @Query("update Document d set d.size = d.size + 1 where d.id = :id")
    void increaseSize(Long id);
}
