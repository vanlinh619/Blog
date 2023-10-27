package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.DocumentSectionRequest;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DocumentSectionService extends EntityService<DocumentSection, Long> {
    DocumentSection addSection(DocumentSectionRequest sectionRequest, User author);
    DocumentSection getByIdAndAuthor(Long id, User author);
    List<DocumentSection> findAllByDocument(Document document, @Nonnull Sort sort);
    void increaseSize(Long id);
}
