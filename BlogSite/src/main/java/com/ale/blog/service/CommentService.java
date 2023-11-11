package com.ale.blog.service;

import com.ale.blog.entity.Comment;
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

    Page<Comment> findAllByPostSlug(@Nullable User owner, @Nonnull String postSlug, @Nonnull QueryRequest queryRequest);
}
