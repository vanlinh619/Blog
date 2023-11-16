package com.ale.blog.service;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.NotificationType;
import com.ale.blog.handler.mapper.CommentMapper;
import com.ale.blog.handler.mapper.pojo.request.CommentRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.state.BroadcastType;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.utils.Format;
import com.ale.blog.repository.CommentRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService, EntityService<Comment, Long> {
    private final CommentRepository commentRepository;
    private final BroadcastService broadcastService;
    private final PostService postService;
    private final CommentMapper commentMapper;
    private final NotificationService notificationService;

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public Comment comment(@Nonnull User author, @Nonnull CommentRequest commentRequest) {
        Post post = postService.getPostBySlug(commentRequest.getPostSlug(), author);

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .createDate(Instant.now())
                .author(author)
                .post(post)
                .childrenSize(0L)
                .build();

        //rely on
        Optional.ofNullable(commentRequest.getReplyCommentId())
                .map(id -> {
                    Comment commentRely = getByIdAndPost(id, post);
                    comment.setReplyFor(commentRely);
                    comment.setReplyUsername(Format.nameOfUser(commentRely.getAuthor()));

                    notificationService.addNotification(commentRely.getAuthor(), author, NotificationType.RELY_COMMENT, String.valueOf(commentRely.getId()));
                    return commentRely.getSuperParent() == null ? commentRely : commentRely.getSuperParent();
                })
                .ifPresent(superParent -> {
                    comment.setSuperParent(superParent);
                    superParent.setChildrenSize(superParent.getChildrenSize() + 1);
                    commentRepository.save(superParent);
                });

        commentRepository.save(comment);

        if(comment.getReplyFor() == null || !comment.getReplyFor().getAuthor().equals(post.getAuthor())) {
            notificationService.addNotification(post.getAuthor(), author, NotificationType.COMMENT_POST, String.valueOf(comment.getId()));
        }
        post.setComment(post.getComment() + 1);
        postService.save(post);
        broadcastService.broadcast("/app/post/" + post.getSlug(), BroadcastType.COMMENT, commentMapper.toCommentResponse(comment));
        return comment;
    }

    @Override
    public Comment getByIdAndPost(@Nonnull Long id, @Nonnull Post post) {
        return commentRepository.findCommentByIdAndPost(id, post)
                .orElseThrow(this::throwIdNotExist);
    }

    @Override
    public Page<Comment> findAllByPostSlug(@Nullable User owner, @Nonnull String postSlug, @Nonnull QueryRequest queryRequest) {
        Post post = postService.getPostBySlug(postSlug, owner);
        return commentRepository.findAllByPostAndSuperParentIsNull(post, Convert.pageRequest(queryRequest));
    }

    @Override
    public Page<Comment> findAllByPostSlugAndSupperComment(@Nullable User owner, @Nonnull String postSlug, @Nonnull Long superCommentId, @Nonnull QueryRequest queryRequest) {
        Post post = postService.getPostBySlug(postSlug, owner);
        Comment superComment = getByIdAndPost(superCommentId, post);
        return commentRepository.findAllByPostAndSuperParent(post, superComment, Convert.pageRequest(queryRequest));
    }


    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
