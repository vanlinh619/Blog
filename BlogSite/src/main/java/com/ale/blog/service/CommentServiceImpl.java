package com.ale.blog.service;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.CommentMapper;
import com.ale.blog.handler.mapper.pojo.request.CommentRequest;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.BroadcastResponse;
import com.ale.blog.handler.mapper.pojo.response.state.BroadcastType;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.repository.CommentRepository;
import com.google.gson.Gson;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService, EntityService<Comment, Long> {
    private final CommentRepository commentRepository;
    private final BroadcastService broadcastService;
    private final PostService postService;
    private final CommentMapper commentMapper;

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

        commentRepository.save(comment);
        broadcastService.broadcast("/app/post/" + post.getSlug(), BroadcastResponse.builder()
                .type(BroadcastType.COMMENT)
                .payload(commentMapper.toCommentResponse(comment))
                .build()
        );
        return comment;
    }

    @Override
    public Page<Comment> findAllByPostSlug(@Nullable User owner, @Nonnull String postSlug, @Nonnull QueryRequest queryRequest) {
        Post post = postService.getPostBySlug(postSlug, owner);
        return commentRepository.findAllByPost(post, Convert.pageRequest(queryRequest));
    }


    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
