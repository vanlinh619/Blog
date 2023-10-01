package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.request.PostRequest;
import com.ale.blog.handler.mapper.request.QueryRequest;

import java.util.List;

public interface PostService extends EntityService<Post, Long> {
    Post createPostArticle(PostRequest postRequest);
    Post getPostBySlug(String slug);
    List<Post> findAllByUsername(String username, QueryRequest queryRequest);
}
