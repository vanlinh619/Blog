package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.request.PostRequest;

public interface PostService extends EntityService<Post, Long> {
    Post createPostArticle(PostRequest postRequest);
}
