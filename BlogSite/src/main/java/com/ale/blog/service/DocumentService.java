package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import org.springframework.data.domain.Page;

public interface DocumentService extends EntityService<Document, Long> {
    Document createDocument(DocumentRequest documentRequest, User author);
    Document getDocumentById(Long id, User author);
    Document getDocumentBySlug(String slug, User author);
    void increaseSize(Long id);
    Page<Document> findAllByAuthor(User author, QueryRequest queryRequest);
}
