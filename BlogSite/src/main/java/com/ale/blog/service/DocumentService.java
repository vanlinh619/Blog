package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;

import java.util.Optional;

public interface DocumentService extends EntityService<Document, Long> {
    Document createDocument(DocumentRequest documentRequest, User author);
    Document getDocumentById(Long id, User author);
    void increaseSize(Long id);
}
