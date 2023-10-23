package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.state.DocumentState;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;
import com.ale.blog.handler.mapper.pojo.response.DocumentResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentMapperImpl implements DocumentMapper {
    private final ModelMapper mapper;

    @Override
    public Document toDocument(DocumentRequest documentRequest) {
        Document document = mapper.map(documentRequest, Document.class);
        document.setState(DocumentState.valueOf(documentRequest.getState()));
        return document;
    }

    @Override
    public DocumentResponse toDocumentResponse(Document document) {
        return mapper.map(document, DocumentResponse.class);
    }
}
