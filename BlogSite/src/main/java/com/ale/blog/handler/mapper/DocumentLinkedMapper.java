package com.ale.blog.handler.mapper;

import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.handler.mapper.pojo.response.DocumentLinkedResponse;

public interface DocumentLinkedMapper extends Mapper {
    DocumentLinkedResponse toDocumentLinkedResponse(DocumentLinked linked);
}
