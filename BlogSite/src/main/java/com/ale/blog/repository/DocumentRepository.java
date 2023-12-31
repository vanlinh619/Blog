package com.ale.blog.repository;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.DocumentState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByIdAndAuthor(Long id, User author);
    Optional<Document> findDocumentBySlugAndAuthor(String slug, User author);

    Optional<Document> findDocumentBySlugAndAuthorAndState(String slug, User author, DocumentState state);
    @Transactional
    @Modifying
    @Query("update Document d set d.size = d.size + 1 where d.id = :id")
    void increaseSize(Long id);
    Page<Document> findAllByAuthorAndState(User author, DocumentState state, Pageable pageable);
    @Query("select d from Document d join Share s on d = s.document where s.shareWith = :owner and s.document.author = :author")
    Page<Document> findAllByAuthorAndShareWith(User author, User owner, Pageable pageable);
}
