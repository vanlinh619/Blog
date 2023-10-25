package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.Share;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.DocumentState;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.entity.state.ShareScope;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.ShareRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.repository.ShareRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;
    private final UserService userService;
    private PostService postService;
    @Autowired
    public void setPostService(@Lazy PostService postService) {
        this.postService = postService;
    }
    private DocumentService documentService;
    @Autowired
    public void setDocumentService(@Lazy DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public Class<Share> getEntityClass() {
        return Share.class;
    }

    @Override
    public boolean isShared(@Nonnull Post post, @Nonnull User user) {
        return Optional.ofNullable(post.getDocument())
                .flatMap(document -> shareRepository.findShareByDocumentAndShareWith(document, user))
                .or(() -> shareRepository.findShareByPostAndShareWith(post, user))
                .isPresent();
    }

    @Override
    public boolean isShared(@Nonnull Document document, @Nonnull User user) {
        return shareRepository.findShareByDocumentAndShareWith(document, user).isPresent();
    }

    @Override
    public Share shareWithUser(@Nonnull ShareRequest shareRequest, @Nonnull User author) {
        User user = userService.getByUsername(shareRequest.getUsername());
        Share share = Share.builder()
                .shareWith(user)
                .build();
        switch (ShareScope.valueOf(shareRequest.getShareScope())) {
            case POST -> {
                share.setPost(postShared(shareRequest.getObjectId(), author));
            }
            case DOCUMENT -> {
                share.setDocument(documentShared(shareRequest.getObjectId(), author));
            }
            default -> {
                throw new AppException(DataResponse.builder()
                        .status(Status.FAILED)
                        .code(MessageCode.SERVER_FAILED)
                        .message("Share service")
                        .build());
            }
        }
        shareRepository.save(share);
        return share;
    }

    @Override
    public Post postShared(@Nonnull Long postId, @Nonnull User author) {
        Post post = postService.getByIdAndAuthor(postId, author);
        if (post.getState() == PostState.PUBLIC) {
            throw new AppException(DataResponse.builder()
                    .status(Status.FAILED)
                    .code(MessageCode.ARGUMENT_IS_INVALID)
                    .message(StaticMessage.STATE_PUBLIC)
                    .build());
        }
        post.setState(PostState.SHARE);
        postService.save(post);
        return post;
    }

    @Override
    public Document documentShared(@Nonnull Long documentId, @Nonnull User author) {
        Document document = documentService.getDocumentById(documentId, author);
        if (document.getState() == DocumentState.PUBLIC) {
            throw new AppException(DataResponse.builder()
                    .status(Status.FAILED)
                    .code(MessageCode.ARGUMENT_IS_INVALID)
                    .message(StaticMessage.STATE_PUBLIC)
                    .build());
        }
        document.setState(DocumentState.SHARE);
        documentService.save(document);
        return document;
    }
}
