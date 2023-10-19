package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.handler.mapper.pojo.request.DocumentSectionRequest;
import com.ale.blog.handler.mapper.pojo.response.DocumentSectionResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentSectionMapperImpl implements DocumentSectionMapper {
    private final ModelMapper mapper;
    @Override
    public DocumentSection toDocumentSection(DocumentSectionRequest documentSectionRequest) {
        DocumentSection section = mapper.map(documentSectionRequest, DocumentSection.class);
        return section;
    }

    @Override
    public DocumentSectionResponse toDocumentSectionResponse(DocumentSection section) {
        return mapper.map(section, DocumentSectionResponse.class);
    }
}
