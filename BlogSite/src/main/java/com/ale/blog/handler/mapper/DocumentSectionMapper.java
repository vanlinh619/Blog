package com.ale.blog.handler.mapper;

import com.ale.blog.entity.DocumentSection;
import com.ale.blog.handler.mapper.pojo.request.DocumentSectionRequest;
import com.ale.blog.handler.mapper.pojo.response.DocumentSectionResponse;

public interface DocumentSectionMapper extends Mapper{
    DocumentSection toDocumentSection(DocumentSectionRequest documentSectionRequest);
    DocumentSectionResponse toDocumentSectionResponse(DocumentSection section);
}
