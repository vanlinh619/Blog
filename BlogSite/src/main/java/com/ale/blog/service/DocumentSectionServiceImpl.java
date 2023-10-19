package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.DocumentSectionMapper;
import com.ale.blog.handler.mapper.pojo.request.DocumentSectionRequest;
import com.ale.blog.repository.DocumentSectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DocumentSectionServiceImpl implements DocumentSectionService {
    private final DocumentSectionMapper sectionMapper;
    private final DocumentService documentService;
    private final DocumentSectionRepository sectionRepository;

    @Override
    public DocumentSection addSection(DocumentSectionRequest sectionRequest, User author) {
        Document document = documentService.getDocumentById(sectionRequest.getDocumentId(), author);
        DocumentSection section = sectionMapper.toDocumentSection(sectionRequest);
        section.setSize(0);
        section.setPriority(document.getSize());
        section.setDocument(document);
        sectionRepository.save(section);

        //Update size document
        documentService.increaseSize(document);
        return section;
    }

    @Override
    public DocumentSection getByIdAndAuthor(Long id, User author) {
        return sectionRepository.findDocumentSectionByIdAndDocument_Author(id, author).orElseThrow(this::throwIdNotExist);
    }

    @Override
    public void increaseSize(DocumentSection section) {
        section.setSize(section.getSize() + 1);
        sectionRepository.save(section);
    }

    @Override
    public Class<DocumentSection> getEntityClass() {
        return DocumentSection.class;
    }
}
