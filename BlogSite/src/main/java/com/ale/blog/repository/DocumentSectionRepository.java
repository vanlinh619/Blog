package com.ale.blog.repository;

import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DocumentSectionRepository extends JpaRepository<DocumentSection, Long> {
    Optional<DocumentSection> findDocumentSectionByIdAndDocument_Author(Long id, User author);
    @Transactional
    @Modifying
    @Query("update DocumentSection d set d.size = d.size + 1 where d.id = :id")
    void increaseSize(Long id);
}
