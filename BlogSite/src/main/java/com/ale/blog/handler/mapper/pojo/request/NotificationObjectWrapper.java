package com.ale.blog.handler.mapper.pojo.request;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationObjectWrapper {
    private Post post;
    private Comment comment;
}
