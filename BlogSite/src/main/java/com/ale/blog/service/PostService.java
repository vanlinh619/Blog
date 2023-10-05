package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.request.PostRequest;
import com.ale.blog.handler.mapper.request.QueryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService extends EntityService<Post, Long> {
    Post createPostArticle(PostRequest postRequest);
    Post getPostBySlug(String slug);
    Page<Post> findAllByUsername(String username, QueryRequest queryRequest);
    Page<Post> findAllByCategory(Category category, QueryRequest queryRequest);
}
