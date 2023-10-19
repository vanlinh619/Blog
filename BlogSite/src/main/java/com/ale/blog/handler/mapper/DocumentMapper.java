package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Document;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;
import com.ale.blog.handler.mapper.pojo.response.DocumentResponse;

public interface DocumentMapper extends Mapper {
    Document toDocument(DocumentRequest documentRequest);
    DocumentResponse toDocumentResponse(Document document);
}
