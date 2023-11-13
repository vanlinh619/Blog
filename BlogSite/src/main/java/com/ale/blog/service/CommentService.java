package com.ale.blog.service;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.CommentRequest;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface CommentService {
    Comment comment(@Nonnull User author, @Nonnull CommentRequest commentRequest);
    Comment getByIdAndPost(@Nonnull Long id, @Nonnull Post post);
    Page<Comment> findAllByPostSlug(@Nullable User owner, @Nonnull String postSlug, @Nonnull QueryRequest queryRequest);
    Page<Comment> findAllByPostSlugAndSupperComment(@Nullable User owner, @Nonnull String postSlug, @Nonnull Long superCommentId, @Nonnull QueryRequest queryRequest);
}
