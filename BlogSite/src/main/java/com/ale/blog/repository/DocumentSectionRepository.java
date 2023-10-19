package com.ale.blog.repository;

import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentSectionRepository extends JpaRepository<DocumentSection, Long> {
    Optional<DocumentSection> findDocumentSectionByIdAndDocument_Author(Long id, User author);
}
