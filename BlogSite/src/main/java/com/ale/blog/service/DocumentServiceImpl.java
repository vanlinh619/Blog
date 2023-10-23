package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.entity.state.SlugType;
import com.ale.blog.handler.mapper.DocumentMapper;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.utils.Format;
import com.ale.blog.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentMapper documentMapper;
    private final SlugIdService slugIdService;
    private final DocumentRepository documentRepository;

    @Override
    public Document createDocument(DocumentRequest documentRequest, User author) {
        Document document = documentMapper.toDocument(documentRequest);
        document.setAuthor(author);
        document.setSize(0);
        document.setCreateDate(Instant.now());
        document.setSlug(slugIdService.getId(SlugType.DOCUMENT) + "-" + Format.toHref(document.getTitle()));
        return documentRepository.save(document);
    }

    @Override
    public Document getDocumentById(Long id, User author) {
        return documentRepository.findDocumentByIdAndAuthor(id, author).orElseThrow(this::throwIdNotExist);
    }

    @Override
    public Document getDocumentBySlug(String slug, User author) {
        return documentRepository.findDocumentBySlugAndAuthorAndState(slug, author, PostState.PUBLIC)
                .orElseThrow(this::throwIdNotExist);
    }

    @Override
    public void increaseSize(Long id) {
        documentRepository.increaseSize(id);
    }

    @Override
    public Page<Document> findAllByAuthor(User author, QueryRequest queryRequest) {
        return documentRepository.findAllByAuthorAndState(author, PostState.PUBLIC, Convert.pageRequest(queryRequest));
    }

    @Override
    public Class<Document> getEntityClass() {
        return Document.class;
    }
}
