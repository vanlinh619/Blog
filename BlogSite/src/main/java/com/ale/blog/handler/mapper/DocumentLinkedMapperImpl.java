package com.ale.blog.handler.mapper;

import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.handler.mapper.pojo.response.DocumentLinkedResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DocumentLinkedMapperImpl implements DocumentLinkedMapper {
    private final ModelMapper mapper;
    @Override
    public DocumentLinkedResponse toDocumentLinkedResponse(DocumentLinked linked) {
        return mapper.map(linked, DocumentLinkedResponse.class);
    }
}
