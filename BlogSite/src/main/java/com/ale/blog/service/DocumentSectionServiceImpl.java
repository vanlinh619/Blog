package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.DocumentSectionMapper;
import com.ale.blog.handler.mapper.pojo.request.DocumentSectionRequest;
import com.ale.blog.repository.DocumentSectionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentSectionServiceImpl implements DocumentSectionService {
    private final DocumentSectionMapper sectionMapper;
    private final DocumentSectionRepository sectionRepository;
    private DocumentService documentService;
    @Autowired
    public void setDocumentService(@Lazy DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public DocumentSection addSection(DocumentSectionRequest sectionRequest, User author) {
        Document document = documentService.getDocumentById(sectionRequest.getDocumentId(), author);
        DocumentSection section = sectionMapper.toDocumentSection(sectionRequest);
        section.setSize(0);
        section.setPriority(document.getSize());
        section.setDocument(document);
        sectionRepository.save(section);

        //Update size document
        documentService.increaseSize(document.getId());
        return section;
    }

    @Override
    public DocumentSection getByIdAndAuthor(Long id, User author) {
        return sectionRepository.findDocumentSectionByIdAndDocument_Author(id, author).orElseThrow(this::throwIdNotExist);
    }

    @Override
    public List<DocumentSection> findAllByDocument(Document document) {
        return sectionRepository.findAllByDocument(document);
    }

    @Override
    public void increaseSize(Long id) {
        sectionRepository.increaseSize(id);
    }

    @Override
    public Class<DocumentSection> getEntityClass() {
        return DocumentSection.class;
    }
}
