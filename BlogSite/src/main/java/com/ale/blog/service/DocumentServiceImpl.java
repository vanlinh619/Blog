package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.DocumentState;
import com.ale.blog.entity.state.SlugType;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.DocumentMapper;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.utils.Format;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.repository.DocumentRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentMapper documentMapper;
    private final SlugIdService slugIdService;
    private final DocumentRepository documentRepository;
    private final ShareService shareService;
    private final DocumentSectionService sectionService;
    private final DocumentLinkedService linkedService;

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
        return documentRepository.findDocumentBySlugAndAuthorAndState(slug, author, DocumentState.PUBLIC)
                .orElseThrow(this::throwIdNotExist);
    }

    @Override
    public Document getDocumentBySlug(@Nonnull String slug, @Nonnull User author, @Nullable User owner) {
        return documentRepository.findDocumentBySlugAndAuthor(slug, author)
                .map(document -> documentWithPermission(document, owner)
                        .orElseThrow(() -> new AppException(DataResponse.builder()
                                .code(MessageCode.UN_AUTHORIZE)
                                .status(Status.FAILED)
                                .build())
                        )
                )
                .orElseThrow(() -> new AppException(DataResponse.builder()
                        .code(MessageCode.NOT_FOUND)
                        .status(Status.FAILED)
                        .message(StaticMessage.SLUG_NOT_FOUND)
                        .build())
                );
    }

    @Override
    public void increaseSize(Long id) {
        documentRepository.increaseSize(id);
    }

    @Override
    public Document getEntriesOfDocument(@Nonnull Document document) {
        document.setSections(sectionService.findAllByDocument(document).stream()
                .peek(section -> section.setLinked(linkedService.findAllBySection(section)))
                .toList()
        );
        return document;
    }

    @Override
    public Page<Document> findAllByAuthor(User author, QueryRequest queryRequest) {
        return documentRepository.findAllByAuthorAndState(author, DocumentState.PUBLIC, Convert.pageRequest(queryRequest));
    }

    @Override
    public Page<Document> findAllByAuthor(@Nonnull User author, @Nullable User owner, @Nonnull QueryRequest queryRequest) {

        return documentRepository.findAllByAuthorAndState(author, DocumentState.PUBLIC, Convert.pageRequest(queryRequest));
    }

    @Override
    public Document save(@Nonnull Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Optional<Document> documentWithPermission(@Nonnull Document document, @Nullable User owner) {
        if (Objects.requireNonNull(document.getState()) == DocumentState.PUBLIC) {
            return Optional.of(document);
        }
        if (owner == null) return Optional.empty();
        return UserUtil.isOwner(owner, document.getAuthor())
                ? Optional.of(document)
                : shareService.isShared(document, owner)
                ? Optional.of(document)
                : Optional.empty();
    }

    @Override
    public Class<Document> getEntityClass() {
        return Document.class;
    }
}
