package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.pojo.PostRequest;

import java.util.Map;

public interface PostService extends EntityService<Post, Long> {
    Post createPostArticle(PostRequest postRequest);
}
