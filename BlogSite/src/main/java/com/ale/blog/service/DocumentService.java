package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.DocumentState;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface DocumentService extends EntityService<Document, Long> {
    Document createDocument(DocumentRequest documentRequest, User author);
    Document getDocumentById(Long id, User author);
    Document getDocumentBySlug(String slug, User author);
    Document getDocumentBySlug(@Nonnull String slug, @Nonnull User author, @Nullable User owner);
    void increaseSize(Long id);
    Optional<Document> getEntriesOfDocument(@Nullable Document document);
    Page<Document> findAllByAuthor(@Nonnull User author, @Nonnull DocumentState state, @Nonnull QueryRequest queryRequest);
    Page<Document> findAllByAuthor(@Nonnull User author, @Nullable User owner, @Nonnull DocumentState state, @Nonnull QueryRequest queryRequest);
    Document save(@Nonnull Document document);
    Optional<Document> documentWithPermission(@Nonnull Document document, @Nullable User owner);
    Optional<Document> isDisplayDocument(@Nonnull Document document, @Nullable User owner);
}
