package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Comment;
import com.ale.blog.handler.mapper.pojo.response.CommentResponse;

public interface CommentMapper extends Mapper {
    CommentResponse toCommentResponse(Comment comment);
}
